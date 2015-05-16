package by.mogilev.controller;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.exception.SendingNotificationsException;
import by.mogilev.model.*;
import by.mogilev.service.CourseService;
import by.mogilev.service.MailService;
import by.mogilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Администратор on 29.03.2015.
 */
@Controller
public class ParticipantAndReminderController {
    public final static String PARTICIPANT_LIST = "/participantsList/{course.id}";
    public final static String EVAL_REMINDER = "/evaluationReminder/{course.id}";
    public final static String SUBSCRIBE = "/subscribePage";
    public final static String ATTENDEE = "/attendeePage";
    public final static String ATTENDEE_LIST = "/attendeeList/{id}";


    @Autowired
    private CourseService courseService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = PARTICIPANT_LIST, method = RequestMethod.GET)
    public ModelAndView participantsListGET(@PathVariable("course.id") final Integer id) throws NotFoundCourseException {
        try {
            ModelAndView mav = new ModelAndView("participantsList");
            mav.addObject("checkCourse", courseService.getCourse(id));
            mav.addObject("participants", courseService.getCourse(id).getSubscribers());
            return mav;
        } catch (NotFoundCourseException e) {
            ModelAndView mavExc = new ModelAndView("informationBoard");
            mavExc.addObject("modalTitle", "Ooops...");
            mavExc.addObject("modalMessage", e.toString());
            return mavExc;
        }
    }

    @RequestMapping(value = PARTICIPANT_LIST, method = RequestMethod.POST)
    public ModelAndView participantsListPOST(@PathVariable("course.id") final Integer id,
                                             @RequestParam(value = "selectParticipants", required = false) final String select)
            throws NotFoundCourseException {
        try {
            ModelAndView mav = new ModelAndView("participantsList");
            Set<User> participants = new HashSet<User>();
            if ("All Participants".equals(select))
                participants = courseService.getCourse(id).getSubscribers();
            else if ("Current Attendees".equals(select))
                participants = courseService.getCourse(id).getAttenders();

            mav.addObject("checkCourse", courseService.getCourse(id));
            mav.addObject("participants", participants);
            return mav;
        } catch (NotFoundCourseException e) {
            ModelAndView mavExc = new ModelAndView("informationBoard");
            mavExc.addObject("modalTitle", "Ooops...");
            mavExc.addObject("modalMessage", e.toString());
            return mavExc;
        }
    }

    @RequestMapping(value = EVAL_REMINDER, method = RequestMethod.GET)
    public ModelAndView reminder(@PathVariable("course.id") final Integer id) throws NotFoundCourseException {
        ModelAndView mav = new ModelAndView("evaluationReminder");
        try {
            mav.addObject("checkCourse", courseService.getCourse(id));
            return mav;
        } catch (NotFoundCourseException e) {
            ModelAndView mavExc = new ModelAndView("informationBoard");
            mavExc.addObject("modalTitle", "Ooops...");
            mavExc.addObject("modalMessage", e.toString());
            return mavExc;
        }
    }


    @RequestMapping(value = EVAL_REMINDER, method = RequestMethod.POST)
    public ModelAndView reminder(@PathVariable("course.id") final Integer id, final HttpServletRequest request)
            throws AddressException, NotFoundCourseException, NotFoundUserException {
        ModelAndView mav = new ModelAndView("evaluationReminder");
        String userName = userService.getUserFromSession(request);
        try {
            Course course = courseService.getCourse(id);
            mav.addObject("checkCourse", course);
            InternetAddress[] emails = mailService.getRecipientAtt(course);
            mailService.sendEmail(id, Notification.EVALUATION_REMINDER, emails, userName, "");

            mav.addObject("modalTitle", "Sending");
            mav.addObject("modalMessage", "Notifications send!");
            return mav;
        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");
        } catch (NotFoundCourseException e) {
            mav.addObject("modalTitle", "Ooops...");
            mav.addObject("modalMessage", e.toString());
            return mav;
        } catch (AddressException e) {
            try {
                throw new SendingNotificationsException(courseService.getCourse(id), e.toString());
            } catch (SendingNotificationsException e1) {
                InternetAddress[] email = InternetAddress.parse(courseService.getCourse(id).getLector().getEmail());
                e1.sendExceptionEmail(email, userName);
                mav.addObject("modalTitle", "Sending");
                mav.addObject("modalMessage", "Notifications send!");
                return mav;
            }

        }
    }

    @RequestMapping(value = SUBSCRIBE, method = RequestMethod.GET)
    public ModelAndView SubscrGet(final HttpServletRequest request) throws NotFoundUserException {
        ModelAndView mav = new ModelAndView("subscribePage");
        try {
            List<Course> coursesForList = courseService.getAllCourse();
            String userName = userService.getUserFromSession(request);
            List<Course> coursesForUser = userService.getCoursesSubscribeOfUser(userName);
            mav.addObject("nameCourses", coursesForList);
            mav.addObject("coursesForUser", coursesForUser);
            return mav;

        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");
        }
    }

    @RequestMapping(value = SUBSCRIBE, method = RequestMethod.POST)
    public ModelAndView SubscPost(
            @RequestParam(value = "selectCourse", required = false) final Integer id_course,
            @RequestParam(value = "selectCategory", required = false) final String selectCategory,
            @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action,
            HttpServletRequest request)
            throws NotFoundCourseException, NotFoundUserException, AddressException {
        if (action == null) action = ActionsOnPage.SELECT;
        String userName = userService.getUserFromSession(request);
        ModelAndView mav = new ModelAndView("subscribePage");
        String message = "";
        try {
            switch (action) {
                case ADD_IN_SUBSCR: {
                    if (userService.addInSubscribers(userName, id_course))
                        message = "You are subscribed!";
                    else
                        message = "You can't subscribe twice!";
                    break;
                }
                case REMOVE_FROM_SUBSCR: {
                    if (userService.removeFromSubscribers(userName, id_course))
                        message = "You delete from Subscribers this course";
                    else
                        message = "You can't delete because you don't subscribe!";
                    break;
                }
                case SELECT: {
                    List<Course> coursesForList = courseService.getSelected(selectCategory);
                    mav.addObject("nameCourses", coursesForList);
                    List<Course> coursesForUser = userService.getCoursesSubscribeOfUser(userName);
                    mav.addObject("coursesForUser", coursesForUser);
                    break;
                }
            }

            mav.addObject("modalTitle", "Subscribe");
            mav.addObject("modalMessage", message);
            return mav;

        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");

        } catch (NotFoundCourseException e) {
            ModelAndView mavExc = new ModelAndView("subscribePage");
            List<Course> coursesForList = courseService.getSelected(selectCategory);
            mavExc.addObject("nameCourses", coursesForList);
            mavExc.addObject("modalTitle", "Ooops...");
            mavExc.addObject("modalMessage", "You don't select course");
            return mavExc;
        } catch (AddressException e) {
            try {
                throw new SendingNotificationsException(courseService.getCourse(id_course), e.toString());
            } catch (SendingNotificationsException e1) {
                InternetAddress[] email = InternetAddress.parse(courseService.getCourse(id_course).getLector().getEmail());
                e1.sendExceptionEmail(email, userName);
                mav.addObject("modalTitle", "Subscribe");
                mav.addObject("modalMessage", message);
                return mav;
            }

        }

    }

    @RequestMapping(value = ATTENDEE, method = RequestMethod.GET)
    public ModelAndView AttendeePageGet(final HttpServletRequest request) throws NotFoundUserException {
        try {
            ModelAndView mav = new ModelAndView("attendeePage");
            List<Course> courses = userService.getCoursesSubscribeOfUser(userService.getUserFromSession(request));
            Set<Course> coursesForList = new HashSet<Course>();
            for (Course c : courses) {
                if (c.getAttenders().size() < Course.MAX_COUNT_ATT && (CourseStatus.DELIVERED.equals(c.getCourseStatus())
                        || CourseStatus.APPROVE_KNOWLEDGE_MANAGER.equals(c.getCourseStatus()))) {
                    coursesForList.add(c);
                }
            }
            String userName = userService.getUserFromSession(request);
            List<Course> coursesForUser = userService.getCoursesAttendeeOfUser(userName);
            mav.addObject("coursesForUser", coursesForUser);
            mav.addObject("nameCourses", coursesForList);
            return mav;

        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");

        }
    }

    @RequestMapping(value = ATTENDEE, method = RequestMethod.POST)
    public ModelAndView AttendeePagePost(
            @RequestParam(value = "selectCourse", required = false) final Integer id_course,
            @RequestParam(value = "selectCategory", required = false) final String selectCategory,
            @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action,
            HttpServletRequest request, Model model)
            throws NotFoundCourseException, NotFoundUserException {

        ModelAndView mav = new ModelAndView("attendeePage");
        List<Course> coursesForList = new ArrayList<Course>();
        try {

            if (ActionsOnPage.ADD_IN_ATT.equals(action)) {
                if (id_course == null) throw new NotFoundCourseException();
                model.addAttribute("id", id_course);
                return new ModelAndView("redirect:" + "/attendeeList/{id}");
            }

            List<Course> subcrCourse = userService.getCoursesSubscribeOfUser(userService.getUserFromSession(request));

            if ("All".equals(selectCategory))
                coursesForList = subcrCourse;
            else {

                for (Course c : subcrCourse) {
                    if (c.getCategory().equals(selectCategory))
                        coursesForList.add(c);
                }
            }
            mav.addObject("nameCourses", coursesForList);
            return mav;

        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");

        } catch (NotFoundCourseException e) {
            mav.addObject("modalTitle", "Ooops...");
            mav.addObject("modalMessage", "You don't select course");
            return mav;

        }

    }

    @RequestMapping(value = ATTENDEE_LIST, method = RequestMethod.GET)
    public ModelAndView AttendeeListGet(@PathVariable("id") final Integer id, final HttpServletRequest request)
            throws NotFoundCourseException {
        ModelAndView mav = new ModelAndView("attendeeList");
        try {
            String userName = userService.getUserFromSession(request);
            mav.addObject("checkCourse", courseService.getCourse(id));
            mav.addObject("attendee", courseService.getCourse(id).getAttenders());
            mav.addObject("attCourseOfUser", userService.getCoursesAttendeeOfUser(userName));
            return mav;

        } catch (NotFoundCourseException e) {
            ModelAndView mavExc = new ModelAndView("informationBoard");
            mavExc.addObject("modalTitle", "Ooops...");
            mavExc.addObject("modalMessage", e.toString());
            return mavExc;
        } catch (NotFoundUserException e) {
            return new ModelAndView("signin");
        }
    }

    @RequestMapping(value = ATTENDEE_LIST, method = RequestMethod.POST)
    public ModelAndView AttendeeListPost(@PathVariable("id") final Integer id, final Principal principal,
                                         @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action)
            throws Exception {
        ModelAndView mav = new ModelAndView("attendeeList");
        try {
            mav.addObject("checkCourse", courseService.getCourse(id));
            String message = "";
            switch (action) {
                case ADD_IN_ATT:
                    if (userService.addInAttSet(principal.getName(), id))
                        message = "You include in attenders list!";
                    else
                        message = "You already include in Attenders!";
                    break;

                case REMOTE_FROM_ATT:
                    if (userService.removeFromAttSet(principal.getName(), id))
                        message = "You delete from attenders list!";
                    else
                        message = "You can't remove from Attenders because you don't include!";
                    break;
            }
            mav.addObject("modalTitle", "Participate");
            mav.addObject("modalMessage", message);
            mav.addObject("attendee", courseService.getCourse(id).getAttenders());

            return mav;

        } catch (NotFoundCourseException e) {

            mav.addObject("modalTitle", "Ooops...");
            mav.addObject("modalMessage", e.toString());
            return mav;
        }

    }
}
