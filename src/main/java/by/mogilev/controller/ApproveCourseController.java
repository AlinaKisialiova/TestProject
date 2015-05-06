package by.mogilev.controller;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.Course;
import by.mogilev.model.CourseStatus;
import by.mogilev.model.Notification;
import by.mogilev.model.UserRole;
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

/**
 * Created by akiseleva on 13.04.2015.
 */
@Controller
public class ApproveCourseController {
    public final String APPROVE_COURSE_GET = "/approveCourse/{course.id}";
    public final String APPROVE_COURSE_POST = "/approveCourse/{course.id}";

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = APPROVE_COURSE_GET, method = RequestMethod.GET)
    public ModelAndView approveCourseGet(@PathVariable("course.id") Integer id) throws NotFoundCourseException {
        ModelAndView mav= new ModelAndView("approveCourse");
        try {
            mav.addObject("courseList", courseService.getCourse(id));
            return mav;
        }
        catch (NotFoundCourseException ex) {
            mav.addObject("modalTitle", "Ooops...");
            mav.addObject("modalMessage", ex.toString());
            return mav;
        }
    }

    @RequestMapping(value = APPROVE_COURSE_POST, method = RequestMethod.POST)
    public ModelAndView approveCoursePost(HttpServletRequest request, @PathVariable("course.id") Integer id, @RequestParam("approveToServ") String approve,
                                          @RequestParam("reasonToServ") String reason, @RequestParam("manager") UserRole manager) throws AddressException, NotFoundCourseException, NotFoundUserException {
try {
    Course appCourse = courseService.getCourse(id);
    String userName = userService.getUserFromSession(request);
    switch (manager) {
        case DEPARTMENT_MANAGER:
            if ("approve".equals(approve)) {
                appCourse.setCourseStatus(CourseStatus.APPROVE_DEPARTMENT_MANAGER);
                InternetAddress[] emails = new InternetAddress[]{
                        new InternetAddress("aflamma@yandex.ru")};
                mailService.sendEmail(id, Notification.COURSE_APPOVAL_STATUS, emails, userName);
            } else {
                appCourse.setCourseStatus(CourseStatus.NOT_APPROVE);
                InternetAddress[] emails = new InternetAddress[]{new InternetAddress(appCourse.getLector().getEmail())};
                mailService.sendEmail(id, Notification.COURSE_REJECTED, emails, userName);
            }
            break;


        case KNOWLEDGE_MANAGER:
            if ("approve".equals(approve)) {
                appCourse.setCourseStatus(CourseStatus.APPROVE_KNOWLEDGE_MANAGER);

                InternetAddress[] emails = mailService.getRecipient(appCourse);

                mailService.sendEmail(id, Notification.NEW_COURSE_ADDED, emails, userName);
            } else {
                appCourse.setCourseStatus(CourseStatus.APPROVE_DEPARTMENT_MANAGER);
                InternetAddress[] emails = new InternetAddress[]{new InternetAddress(appCourse.getLector().getEmail())};
                mailService.sendEmail(id, Notification.COURSE_REJECTED, emails, userName);
            }
            break;
    }

    appCourse.setKnowledgeManagerReason(reason);
    courseService.updateCourse(appCourse);

    ModelAndView mav = new ModelAndView("informationBoard");
    mav.addObject("courseList", courseService.getAllCourse());
    return mav;
}
catch (NotFoundUserException ex) {
    return new ModelAndView("signin");
}
catch (NotFoundCourseException ex) {
    ModelAndView mav = new ModelAndView("informationBoard");
    mav.addObject("modalTitle", "Ooops...");
    mav.addObject("modalMessage", ex.toString());
    return mav;
}
    }
}
