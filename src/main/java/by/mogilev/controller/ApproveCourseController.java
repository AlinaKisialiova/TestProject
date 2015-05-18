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
    public ModelAndView approveCourseGet(@PathVariable("course.id") final Integer id, final HttpServletRequest request)
            throws NotFoundCourseException, NotFoundUserException {
        ModelAndView mav = new ModelAndView("approveCourse");

            mav.addObject("courseList", courseService.getCourse(id));
            User user = userService.getUser(userService.getUserFromSession(request));
            mav.addObject("nameUser", user.getName());
            String reasonDM = courseService.getCourse(id).getDepartmentManagerReason();
            mav.addObject("reasonDM", reasonDM);
            String reasonKM = courseService.getCourse(id).getKnowledgeManagerReason();
            mav.addObject("reasonKM", reasonKM);
            return mav;

    }

    @RequestMapping(value = APPROVE_COURSE, method = RequestMethod.POST)
    public ModelAndView approveCoursePost(final HttpServletRequest request, @PathVariable("course.id") final Integer id,
                                          @RequestParam("approveToServ") final String approve,
                                          @RequestParam("reasonToServ") final String reason,
                                          @RequestParam("manager") final UserRole manager)
            throws AddressException, NotFoundCourseException, NotFoundUserException {
        String userName = userService.getUserFromSession(request);

            Course appCourse = courseService.getCourse(id);
            switch (manager) {
                case DEPARTMENT_MANAGER:
                    if ("approve".equals(approve)) {
                        appCourse.setCourseStatus(CourseStatus.APPROVE_DEPARTMENT_MANAGER);
                        appCourse.setDepartmentManagerReason(reason);
                        InternetAddress[] emails = new InternetAddress[]{
                                new InternetAddress("aflamma@yandex.ru")};
                        mailService.sendEmail(id, Notification.COURSE_APPOVAL_STATUS, emails, userName,"");
                    } else {
                        appCourse.setCourseStatus(CourseStatus.NOT_APPROVE);
                        InternetAddress[] emails = new InternetAddress[]{new InternetAddress(appCourse.getLector().getEmail())};
                        mailService.sendEmail(id, Notification.COURSE_REJECTED, emails, userName,"");
                    }
                    break;

                case KNOWLEDGE_MANAGER:
                    if ("approve".equals(approve)) {
                        appCourse.setCourseStatus(CourseStatus.APPROVE_KNOWLEDGE_MANAGER);
                        appCourse.setKnowledgeManagerReason(reason);
                        InternetAddress[] emails = mailService.getRecipient(appCourse.getSubscribers());
                        mailService.sendEmail(id, Notification.NEW_COURSE_ADDED, emails, userName,"");
                    } else {
                        appCourse.setCourseStatus(CourseStatus.APPROVE_DEPARTMENT_MANAGER);
                        InternetAddress[] emails = new InternetAddress[]{new InternetAddress(appCourse.getLector().getEmail())};
                        mailService.sendEmail(id, Notification.COURSE_REJECTED, emails, userName,"");
                    }
                    break;
            }


            courseService.updateCourse(appCourse);

            ModelAndView mav = new ModelAndView("informationBoard");
            mav.addObject("courseList", courseService.getAllCourse());
            return mav;

//        catch (AddressException e) {
//            try {
//                throw new SendingNotificationsException(courseService.getCourse(id), e.toString());
//            } catch (SendingNotificationsException e1) {
//                InternetAddress[] email = InternetAddress.parse(courseService.getCourse(id).getLector().getEmail());
//                e1.sendExceptionEmail(email,userName);
//                ModelAndView mav = new ModelAndView("informationBoard");
//                mav.addObject("courseList", courseService.getAllCourse());
//                return mav;
//            }
// }


    }


    @RequestMapping(value = APPROVE, method = RequestMethod.GET)
    public ModelAndView approveGet(final HttpServletRequest request) throws AddressException, NotFoundUserException {

        ModelAndView mav = new ModelAndView("approvePage");
        List<Course> coursesForApprove = new ArrayList<Course>();

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

    }

    @RequestMapping(value = APPROVE, method = RequestMethod.POST)
    public ModelAndView approvePost(HttpServletRequest request, Model model,
                                    @RequestParam(value = "selectCourse", required = false) final Integer id_course,
                                    @RequestParam(value = "selectCategory", required = false) final String selectCategory,
                                    @RequestParam(value = "fieldForSubmit", required = false) final ActionsOnPage action)
            throws NotFoundCourseException, NotFoundUserException {
        ModelAndView mav = new ModelAndView("approvePage");

           if (ActionsOnPage.APPROVE.equals(action)) {
               if (id_course == null) throw new NotFoundCourseException();
               model.addAttribute("course.id", id_course);
               return new ModelAndView("redirect:" + "/approveCourse/{course.id}");
           }

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

    }
}
