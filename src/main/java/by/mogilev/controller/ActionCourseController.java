package by.mogilev.controller;

import by.mogilev.model.Course;
import by.mogilev.model.Notification;
import by.mogilev.service.CourseService;
import by.mogilev.service.MailService;
import by.mogilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * Created by akiseleva on 09.03.2015.
 */
@Controller
public class ActionCourseController {
    final String REGISTRATON_COURSE = "/registrationCourse";

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @ModelAttribute("Course")
    public Course newCourse() {
        return new Course();
    }

    @RequestMapping(value = "/registrationCourse", method = RequestMethod.GET)
    public String displayRegCourse() {
        return "registrationCourse";
    }

    @RequestMapping(value = REGISTRATON_COURSE, method = RequestMethod.POST)
    public ModelAndView regCourse(@ModelAttribute("Course")  Course newCourse,  BindingResult result, Model model, HttpSession session, HttpServletRequest request) throws AddressException {
           if (result.hasErrors())
               new ModelAndView("redirect:/informationBoard");

        String userName = "";
        Principal principal = request.getUserPrincipal();
        if (principal != null && principal.getName() != null) {
            userName = principal.getName();
        }
        courseService.registerCourse(newCourse, userName);





            return new ModelAndView("redirect:/informationBoard");

    }

    @RequestMapping(value = "/courseDetails/{course.id}", method = RequestMethod.GET)
    public ModelAndView detailsCourse(@PathVariable("course.id") Integer id) {
        ModelAndView mav= new ModelAndView("courseDetails");
        mav.addObject("checkCourse", courseService.getCourse(id));
        return mav;

    }

    @RequestMapping(value = "/courseDetails/{course.id}", method = RequestMethod.POST)
    public ModelAndView deleteCourse(@PathVariable("course.id") Integer id, HttpServletRequest request) throws AddressException {
        String userName = "";
        Principal principal = request.getUserPrincipal();
        if (principal != null && principal.getName() != null) {
            userName = principal.getName();
        }
    courseService.deleteCourse(id, userName);
        return new ModelAndView("redirect:/informationBoard");

    }

    @RequestMapping(value = "/editCourse/{course.id}", method = RequestMethod.GET)
    public String editRegCourse(@PathVariable("course.id") Integer id, Model model) {
        model.addAttribute("categoryMap", courseService.getCategotyMap());
        model.addAttribute("course", courseService.getCourse(id));
        return "editCourse";
    }

    @RequestMapping(value = "/editCourse/{course.id}", method = RequestMethod.POST)
    public String editCourse(@PathVariable("course.id") Integer id,
                             @ModelAttribute("Course") Course updCourse,
                             HttpServletRequest request, HttpSession session, Model model) throws AddressException {
        model.addAttribute("categoryMap", courseService.getCategotyMap());

        if (! (courseService.isOwner(id, session)) || courseService.getCourse(id) == null)
              return "redirect:/informationBoard";
        updCourse.setId(id);
            courseService.updateCourse(updCourse);

          return "redirect:/informationBoard";
    }


}
