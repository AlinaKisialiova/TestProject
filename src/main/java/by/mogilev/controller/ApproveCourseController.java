package by.mogilev.controller;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
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
import java.util.List;

/**
 * Created by akiseleva on 13.04.2015.
 */
@Controller
public class ApproveCourseController {
    public final String APPROVE_COURSE = "/approveCourse/{course.id}";

    public final String APPROVE = "/approvePage";

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = APPROVE_COURSE, method = RequestMethod.GET)
    public ModelAndView approveCourseGet(@PathVariable("course.id") Integer id, HttpServletRequest request) throws NotFoundCourseException {
        ModelAndView mav= new ModelAndView("approveCourse");
        try {
            mav.addObject("courseList", courseService.getCourse(id));
            User user = userService.getUser(userService.getUserFromSession(request));
            mav.addObject("nameUser", user.getName() );
            return mav;
        }
        catch (NotFoundCourseException ex) {
            mav.addObject("modalTitle", "Ooops...");
            mav.addObject("modalMessage", ex.toString());
            return mav;
        } catch (NotFoundUserException e) {
            return new ModelAndView("signin");
        }
    }

    @RequestMapping(value = APPROVE_COURSE, method = RequestMethod.POST)
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


    @RequestMapping(value = APPROVE, method = RequestMethod.GET)
    public ModelAndView approveGET(HttpServletRequest request) throws AddressException, NotFoundCourseException, NotFoundUserException {
      User user = userService.getUser(userService.getUserFromSession(request));
ModelAndView mav = new ModelAndView("approvePage");
        List<Course> coursesForApprove = courseService.getAllCourse();
        switch (user.getAuthority()) {
            case DEPARTMENT_MANAGER: {
               for (Course c : coursesForApprove) {
                   if (! c.getCourseStatus().equals("") || !c.getCourseStatus().equals(CourseStatus.NOT_APPROVE))
                       coursesForApprove.remove(c);
               }

            }
            case KNOWLEDGE_MANAGER:{
                for (Course c : coursesForApprove) {
                    if (!c.getCourseStatus().equals(CourseStatus.APPROVE_DEPARTMENT_MANAGER))
                        coursesForApprove.remove(c);
                }

            }
        }
        mav.addObject("coursesForApprove", coursesForApprove);
        return mav;
    }

    @RequestMapping(value = APPROVE, method = RequestMethod.POST)
    public ModelAndView approveGETt(HttpServletRequest request, Model model,
                                    @RequestParam(value = "selectCourse", required = false) Integer id_course,
                                    @RequestParam(value = "selectCategory", required = false) String selectCategory) throws NotFoundCourseException {
        if (id_course == null) throw new NotFoundCourseException();
        model.addAttribute("id", id_course);
        return new ModelAndView("redirect:" + "/approveCourse/{id}");

    }
}
