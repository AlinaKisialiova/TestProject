package by.mogilev.controller;

import by.mogilev.model.*;
import by.mogilev.service.CourseService;
import by.mogilev.service.MailService;
import by.mogilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
import java.util.List;
import java.util.Set;

/**
 * Created by akiseleva on 13.04.2015.
 */
@Controller
public class ApproveCourseController {
    private final String APPROVE_COURSE_GET = "/approveCourse/{course.id}";
    private final String APPROVE_COURSE_POST = "/approveCourse/{course.id}";

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = APPROVE_COURSE_GET, method = RequestMethod.GET)
    public ModelAndView approveCourseGet(@PathVariable("course.id") Integer id) {
        ModelAndView mav= new ModelAndView("approveCourse");
        mav.addObject("courseList", courseService.getCourse(id));
        return mav;
    }

    @RequestMapping(value = APPROVE_COURSE_POST, method = RequestMethod.POST)
    public ModelAndView approveCoursePost(HttpServletRequest request, @PathVariable("course.id") Integer id, @RequestParam("approveToServ") String approve,
                                          @RequestParam("reasonToServ") String reason, @RequestParam("manager") UserRole manager) throws AddressException {

        Course appCourse = courseService.getCourse(id);
        String userName = "";
        Principal principal = request.getUserPrincipal();
        if (principal != null && principal.getName() != null) {
            userName = principal.getName();
        }

        switch (manager) {
            case DEPARTMENT_MANAGER :
                if ("approve".equals(approve)) {
                    appCourse.setCourseStatus(CourseStatus.APPROVE_DEPARTMENT_MANAGER);
                    InternetAddress[] emails= new InternetAddress[]{
                            new InternetAddress("aflamma@yandex.ru")};
                    mailService.sendEmail(id, Notification.COURSE_APPOVAL_STATUS, emails, userName);
                }
                else {
                    appCourse.setCourseStatus(CourseStatus.NOT_APPROVE);
                    InternetAddress[] emails = new InternetAddress[]{new InternetAddress(appCourse.getLector().getEmail())};
                    mailService.sendEmail(id, Notification.COURSE_REJECTED,emails , userName);
                }
                break;


            case KNOWLEDGE_MANAGER :
                if ("approve".equals(approve)) {
                    appCourse.setCourseStatus(CourseStatus.APPROVE_KNOWLEDGE_MANAGER);

                    InternetAddress[] emails = mailService.getRecipient(appCourse);

                    mailService.sendEmail(id, Notification.NEW_COURSE_ADDED, emails, userName);
                }
                else {
                    appCourse.setCourseStatus(CourseStatus.APPROVE_DEPARTMENT_MANAGER);
                    InternetAddress[] emails = new InternetAddress[]{new InternetAddress(appCourse.getLector().getEmail())};
                    mailService.sendEmail(id, Notification.COURSE_REJECTED,emails , userName);
                }
                break;
        }

        appCourse.setKnowledgeManagerReason(reason);
        courseService.updateCourse(appCourse);

        ModelAndView mav= new ModelAndView("informationBoard");
        mav.addObject("courseList", courseService.getAllCourse());
        return mav;
    }
}
