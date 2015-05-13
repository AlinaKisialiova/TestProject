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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akiseleva on 13.04.2015.
 */
@Controller
public class ApproveCourseController {
    public final static String APPROVE_COURSE = "/approveCourse/{course.id}";

    public final static String APPROVE = "/approvePage";

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @RequestMapping(value = APPROVE_COURSE, method = RequestMethod.GET)
    public ModelAndView approveCourseGet(@PathVariable("course.id") Integer id, HttpServletRequest request) throws NotFoundCourseException {
        ModelAndView mav = new ModelAndView("approveCourse");
        try {
            mav.addObject("courseList", courseService.getCourse(id));
            User user = userService.getUser(userService.getUserFromSession(request));
            mav.addObject("nameUser", user.getName());
            String reasonDM = courseService.getCourse(id).getDepartmentManagerReason();
            mav.addObject("reasonDM", reasonDM);
            return mav;
        } catch (NotFoundCourseException ex) {
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
                        appCourse.setDepartmentManagerReason(reason);
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
                        appCourse.setKnowledgeManagerReason(reason);
                        InternetAddress[] emails = mailService.getRecipient(appCourse);

                        mailService.sendEmail(id, Notification.NEW_COURSE_ADDED, emails, userName);
                    } else {
                        appCourse.setCourseStatus(CourseStatus.APPROVE_DEPARTMENT_MANAGER);
                        InternetAddress[] emails = new InternetAddress[]{new InternetAddress(appCourse.getLector().getEmail())};
                        mailService.sendEmail(id, Notification.COURSE_REJECTED, emails, userName);
                    }
                    break;
            }


            courseService.updateCourse(appCourse);

            ModelAndView mav = new ModelAndView("informationBoard");
            mav.addObject("courseList", courseService.getAllCourse());
            return mav;
        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");
        } catch (NotFoundCourseException ex) {
            ModelAndView mav = new ModelAndView("informationBoard");
            mav.addObject("modalTitle", "Ooops...");
            mav.addObject("modalMessage", ex.toString());
            return mav;
        }
    }


    @RequestMapping(value = APPROVE, method = RequestMethod.GET)
    public ModelAndView approveGet(HttpServletRequest request) throws AddressException {

        ModelAndView mav = new ModelAndView("approvePage");
        List<Course> coursesForApprove = new ArrayList<Course>();
        try {
            User user = userService.getUser(userService.getUserFromSession(request));
            switch (user.getAuthority()) {
                case DEPARTMENT_MANAGER: {
                    coursesForApprove = courseService.getCourseForDepartmentManager();
                    break;
                }
                case KNOWLEDGE_MANAGER: {
                    coursesForApprove = courseService.getCourseForKnowledgeManagerManager();
                    break;
                }
            }
            mav.addObject("coursesForApprove", coursesForApprove);
            return mav;
        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");
        }
    }

    @RequestMapping(value = APPROVE, method = RequestMethod.POST)
    public ModelAndView approvePost(HttpServletRequest request, Model model,
                                    @RequestParam(value = "selectCourse", required = false) Integer id_course,
                                    @RequestParam(value = "selectCategory", required = false) String selectCategory,
                                    @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action) throws NotFoundCourseException, NotFoundUserException {
        ModelAndView mav = new ModelAndView("approvePage");
        try {
           if (ActionsOnPage.APPROVE.equals(action)) {
               if (id_course == null) throw new NotFoundCourseException();
               model.addAttribute("course.id", id_course);
               return new ModelAndView("redirect:" + "/approveCourse/{course.id}");
           }
            String reasonDM = "";
           List<Course> courses = new ArrayList<Course>();
           User user = userService.getUser(userService.getUserFromSession(request));
           switch (user.getAuthority()) {
               case DEPARTMENT_MANAGER: {
                   courses = courseService.getCourseForDepartmentManager();
                   break;
               }
               case KNOWLEDGE_MANAGER: {
                   courses = courseService.getCourseForKnowledgeManagerManager();
                   break;
               }
           }
           List<Course> coursesForApprove = new ArrayList<Course>();

           if ("All".equals(selectCategory))
               coursesForApprove = courses;
           else {

               for (Course c : courses) {
                   if (c.getCategory().equals(selectCategory))
                       coursesForApprove.add(c);
               }
           }

           mav.addObject("coursesForApprove", coursesForApprove);

           return mav;
       } catch (NotFoundCourseException ex) {
           mav.addObject("modalTitle", "Ooops...");
           mav.addObject("modalMessage", ex.toString());
           return mav;
       }

    }
}
