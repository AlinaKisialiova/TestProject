package by.mogilev.controller;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.*;
import by.mogilev.service.CourseService;
import by.mogilev.service.MailService;
import by.mogilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Администратор on 29.03.2015.
 */
@Controller
public class ParticipantAndReminderController {
    public final String PARTICIPANT_LIST = "/participantsList/{course.id}";
    public final String EVAL_REMINDER = "/evaluationReminder/{course.id}";
    public final String SUBSCRIBE = "/subscribePage";
    public final String ATTENDEE = "/attendeePage";


    @Autowired
    private CourseService courseService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = PARTICIPANT_LIST, method = RequestMethod.GET)
    public ModelAndView participantsListGET(@PathVariable("course.id") Integer id) throws NotFoundCourseException {
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
    public ModelAndView participantsListPOST(@PathVariable("course.id") Integer id,
                                             @RequestParam(value = "selectParticipants", required = false) String select) throws NotFoundCourseException {
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
    public ModelAndView reminder(@PathVariable("course.id") Integer id) throws NotFoundCourseException {
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
    public ModelAndView reminderStartorReset(@PathVariable("course.id") Integer id, HttpServletRequest request) throws AddressException, NotFoundCourseException, NotFoundUserException {
        try {
            ModelAndView mav = new ModelAndView("evaluationReminder");
            Course course = courseService.getCourse(id);
            mav.addObject("checkCourse", course);
            String userName = userService.getUserFromSession(request);
            String message = "";
            if (courseService.startCourse(id, userName)) {
                message = "Course " + course.getNameCourse() + " is started!";
                course.setCourseStatus(CourseStatus.DELIVERED);
                InternetAddress[] emails = mailService.getRecipient(course);
                mailService.sendEmail(id, Notification.EVALUATION_REMINDER, emails, userName);

            } else
                message = "Course dont started!";

            mav.addObject("startMessage", message);
            return mav;
        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");
        } catch (NotFoundCourseException e) {
            ModelAndView mavExc = new ModelAndView("informationBoard");
            mavExc.addObject("modalTitle", "Ooops...");
            mavExc.addObject("modalMessage", e.toString());
            return mavExc;
        }
    }

    @RequestMapping(value = SUBSCRIBE, method = RequestMethod.GET)
    public ModelAndView SubscrGET() {
        ModelAndView mav = new ModelAndView("subscribePage");
        List<Course> coursesForList = courseService.getAllCourse();
        mav.addObject("nameCourses", coursesForList);
        return mav;
    }

    @RequestMapping(value = SUBSCRIBE, method = RequestMethod.POST)
    public ModelAndView SubscPOST(
            @RequestParam(value = "selectCourse", required = false) Integer id_course,
            @RequestParam(value = "selectCategory", required = false) String selectCategory,
            @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action,
            HttpServletRequest request)
            throws NotFoundCourseException, NotFoundUserException, AddressException {
        try {
            ModelAndView mav = new ModelAndView("subscribePage");
            if (ActionsOnPage.SUBSCRIBE.equals(action)) {
                if (id_course == null) throw new NotFoundCourseException();
                String message = "";
                if (userService.addInSubscribers(userService.getUserFromSession(request), id_course))
                    message = "You are subscribed!";
                else
                    message = "You are deleted from subscribe list!!";

                mav.addObject("modalTitle", "Subscribe");
                mav.addObject("modalMessage", message);
            }

            List<Course> coursesForList = courseService.getSelected(selectCategory);
            mav.addObject("nameCourses", coursesForList);
            return mav;

        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");

        } catch (NotFoundCourseException e) {
            ModelAndView mavExc = new ModelAndView("subscribePage");
            List<Course> coursesForList = courseService.getSelected(selectCategory);
            mavExc.addObject("nameCourses", coursesForList);
            mavExc.addObject("modalTitle", "Ooops...");
            mavExc.addObject("modalMessage", "You dont select course");
            return mavExc;

        }
    }

    @RequestMapping(value = ATTENDEE, method = RequestMethod.GET)
    public ModelAndView AttendeePageGET(HttpServletRequest request) throws NotFoundUserException {
        try {
            ModelAndView mav = new ModelAndView("attendeePage");
            Set<Course> coursesForList = userService.getCoursesSubscribeOfUser(userService.getUserFromSession(request));
            mav.addObject("nameCourses", coursesForList);
            return mav;

        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");

        }
    }

    @RequestMapping(value = ATTENDEE, method = RequestMethod.POST)
    public ModelAndView AttendeePagePOST(
            @RequestParam(value = "selectCourse", required = false) Integer id_course,
            @RequestParam(value = "selectCategory", required = false) String selectCategory,
            @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action,
            HttpServletRequest request)
            throws NotFoundCourseException, NotFoundUserException, AddressException {
        try {
            if (ActionsOnPage.ADD_IN_ATT.equals(action)) {
                return new ModelAndView("attendeeList/{id_course}");
            }
            ModelAndView mav = new ModelAndView("attendeePage");
            Set<Course> subcrCourse = userService.getCoursesSubscribeOfUser(userService.getUserFromSession(request));
            Set<Course> coursesForList = new HashSet<Course>();
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

        }
    }
}
