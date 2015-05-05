package by.mogilev.controller;

import by.mogilev.exception.NullIdCourseException;
import by.mogilev.exception.NullUserException;
import by.mogilev.model.Course;
import by.mogilev.model.CourseStatus;
import by.mogilev.model.Notification;
import by.mogilev.model.User;
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
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Администратор on 29.03.2015.
 */
@Controller
public class ParticipantAndReminderController {
    public final String PARTICIPANT_LIST = "/participantsList/{course.id}";
    public final String EVAL_REMINDER = "/evaluationReminder/{course.id}";

    @Autowired
    private CourseService courseService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = PARTICIPANT_LIST, method = RequestMethod.GET)
    public ModelAndView participantsListGET(@PathVariable("course.id") Integer id) throws NullIdCourseException {
        try {
            ModelAndView mav = new ModelAndView("participantsList");
            mav.addObject("checkCourse", courseService.getCourse(id));
            mav.addObject("participants", courseService.getCourse(id).getSubscribers());
            return mav;
        } catch (NullIdCourseException e) {
            ModelAndView mavExc = new ModelAndView("informationBoard");
            mavExc.addObject("excTitle", "Ooops...");
            mavExc.addObject("excMessage", e.toString());
            return mavExc;
        }
    }

    @RequestMapping(value = PARTICIPANT_LIST, method = RequestMethod.POST)
    public ModelAndView participantsListPOST(@PathVariable("course.id") Integer id,
                                             @RequestParam(value = "selectParticipants", required = false) String select) throws NullIdCourseException {
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
        } catch (NullIdCourseException e) {
            ModelAndView mavExc = new ModelAndView("informationBoard");
            mavExc.addObject("excTitle", "Ooops...");
            mavExc.addObject("excMessage", e.toString());
            return mavExc;
        }
    }

    @RequestMapping(value = EVAL_REMINDER, method = RequestMethod.GET)
    public ModelAndView reminder(@PathVariable("course.id") Integer id) throws NullIdCourseException {
        ModelAndView mav = new ModelAndView("evaluationReminder");
        try {
            mav.addObject("checkCourse", courseService.getCourse(id));
            return mav;
        } catch (NullIdCourseException e) {
            ModelAndView mavExc = new ModelAndView("informationBoard");
            mavExc.addObject("excTitle", "Ooops...");
            mavExc.addObject("excMessage", e.toString());
            return mavExc;
        }
    }


    @RequestMapping(value = EVAL_REMINDER, method = RequestMethod.POST)
    public ModelAndView reminderStartorReset(@PathVariable("course.id") Integer id, HttpServletRequest request) throws AddressException, NullIdCourseException, NullUserException {
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
        } catch (NullUserException ex) {
            return new ModelAndView("signin");
        } catch (NullIdCourseException e) {
            ModelAndView mavExc = new ModelAndView("informationBoard");
            mavExc.addObject("excTitle", "Ooops...");
            mavExc.addObject("excMessage", e.toString());
            return mavExc;
        }
    }
}
