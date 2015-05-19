package by.mogilev.controller;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.exception.SendingNotificationsException;
import by.mogilev.exception.TrainingCenterException;
import by.mogilev.model.Course;
import by.mogilev.model.Notification;
import by.mogilev.service.CourseService;
import by.mogilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


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

    @RequestMapping(value = ERROR, method = RequestMethod.GET)
    public ModelAndView displayError(HttpServletRequest request) throws NotFoundUserException, NotFoundCourseException, AddressException {
        ModelAndView mav = new ModelAndView("error");
        TrainingCenterException message = (TrainingCenterException) request.getAttribute("javax.servlet.error.exception");

        if (message instanceof NotFoundUserException)
            return new ModelAndView("signin");

        if (message instanceof SendingNotificationsException) {
        HttpSession session = request.getSession();
        SecurityContext ctx = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication auth = ctx.getAuthentication();
        String userName = auth.getName();
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        char[] reqUriChar = requestUri.toCharArray();
        List<Character> id_courseChar = new ArrayList<Character>();
        for (int i = requestUri.length() - 1; reqUriChar[i] != '/'; i--)
            id_courseChar.add(reqUriChar[i]);

        List<Character> idChar =new ArrayList<Character>();
        for (int i = id_courseChar.size() - 1, j = 0; i >= 0; i--) {
            idChar.add(id_courseChar.get(i));
        }

        String id="";
      for(Character c : idChar )
        id+=c;

        Course course = courseService.getCourse(Integer.valueOf(id));
        InternetAddress[] email = InternetAddress.parse(userService.getUser(userName).getEmail());
        ((SendingNotificationsException) message).sendExceptionEmail(course.getId(), Notification.ADDRESS_EXCEPTION_MESSAGE, email, userName, message);
        }

        mav.addObject("message", message.toString());
        return mav;
    }
}
