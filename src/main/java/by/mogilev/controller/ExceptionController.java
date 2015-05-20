package by.mogilev.controller;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.exception.SendingNotificationsException;
import by.mogilev.exception.TrainingCenterException;
import by.mogilev.model.Course;
import by.mogilev.service.CourseService;
import by.mogilev.service.MailService;
import by.mogilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by akiseleva on 18.05.2015.
 */
@Controller
public class ExceptionController {
    public final static String ERROR = "/error";
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = ERROR, method = RequestMethod.GET)
    public ModelAndView displayError(HttpServletRequest request) throws NotFoundUserException, NotFoundCourseException, AddressException {
        ModelAndView mav = new ModelAndView("error");
        TrainingCenterException message = (TrainingCenterException) request.getAttribute("javax.servlet.error.exception");

        if (message instanceof NotFoundUserException)
            return new ModelAndView("signin");

        if (message instanceof SendingNotificationsException) {
            String userName = userService.getUserFromSession(request);
            int id = mailService.getIdCourseFromUri(request);
            Course course = courseService.getCourse(id);
            InternetAddress[] email = InternetAddress.parse(userService.getUser(userName).getEmail());

            ((SendingNotificationsException) message).sendExceptionEmail(course.getId(), email, userName);
        }

        mav.addObject("message", message.toString());
        return mav;
    }
}
