package by.mogilev.controller;

import by.mogilev.exception.IsNotOwnerException;
import by.mogilev.exception.NullIdCourseException;
import by.mogilev.exception.NullUserException;
import by.mogilev.model.Course;
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

/**
 * Created by akiseleva on 09.03.2015.
 */
@Controller
public class ActionCourseController {
    public final String REGISTRATION_COURSE = "/registrationCourse";
    public final String DETAIL_COURSE = "/courseDetails/{course.id}";
    public final String EDIT_COURSE = "/editCourse/{course.id}";

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

    @RequestMapping(value = REGISTRATION_COURSE, method = RequestMethod.GET)
    public String displayRegCourse() {
        return "registrationCourse";
    }

    @RequestMapping(value = REGISTRATION_COURSE, method = RequestMethod.POST)
    public ModelAndView regCourse(@ModelAttribute("Course") Course newCourse, BindingResult result, Model model,
                                  HttpSession session, HttpServletRequest request) throws AddressException, NullUserException {
        try {
            if (result.hasErrors())
                new ModelAndView("redirect:/informationBoard");
            String userName = userService.getUserFromSession(request);
            courseService.registerCourse(newCourse, userName);
            return new ModelAndView("redirect:/informationBoard");
        } catch (NullUserException ex) {
            return new ModelAndView("signin");

        }
    }

    @RequestMapping(value = DETAIL_COURSE, method = RequestMethod.GET)
    public ModelAndView detailsCourse(@PathVariable("course.id") Integer id) throws NullIdCourseException {
        ModelAndView mav = new ModelAndView("courseDetails");
        try {
            mav.addObject("checkCourse", courseService.getCourse(id));
            return mav;
        } catch (NullIdCourseException e) {
            mav.addObject("excTitle", "Ooops...");
            mav.addObject("excMessage", e.toString());
            return new ModelAndView("courseDetails/{course.id}");
        }

    }

    @RequestMapping(value = DETAIL_COURSE, method = RequestMethod.POST)
    public ModelAndView deleteCourse(@PathVariable("course.id") Integer id, HttpServletRequest request) throws AddressException, NullUserException, NullIdCourseException {
        try {
            String userName = userService.getUserFromSession(request);
            courseService.deleteCourse(id, userName);
            return new ModelAndView("redirect:/informationBoard");

        } catch (NullUserException ex) {
            return new ModelAndView("signin");
        } catch (NullIdCourseException e) {
            ModelAndView mav = new ModelAndView("courseDetails");
            mav.addObject("excTitle", "Ooops...");
            mav.addObject("excMessage", e.toString());
            return new ModelAndView("courseDetails/{course.id}");
        }

    }

    @RequestMapping(value = EDIT_COURSE, method = RequestMethod.GET)
    public String editRegCourse(@PathVariable("course.id") Integer id, Model model) throws NullIdCourseException {
        try {
            model.addAttribute("categoryMap", courseService.getCategotyMap());
            model.addAttribute("course", courseService.getCourse(id));
            return "editCourse";
        } catch (NullIdCourseException e) {
            ModelAndView mav = new ModelAndView("informationBoard");
            mav.addObject("excTitle", "Ooops...");
            mav.addObject("excMessage", e.toString());
            return "informationBoard";
        }
    }

    @RequestMapping(value = EDIT_COURSE, method = RequestMethod.POST)
    public ModelAndView editCourse(@PathVariable("course.id") Integer id,
                             @ModelAttribute("Course") Course updCourse,
                             HttpServletRequest request, Model model) throws AddressException, NullIdCourseException, NullUserException, IsNotOwnerException {
       try {
           model.addAttribute("categoryMap", courseService.getCategotyMap());

           String userName = userService.getUserFromSession(request);
           if (!(courseService.isOwner(id, userName))) throw new IsNotOwnerException();

           updCourse.setId(id);
           updCourse.setLector(userService.getUser(userName));
           courseService.updateCourse(updCourse);
           return new ModelAndView("redirect:/courseDetails/{course.id}");       }
       catch (NullUserException ex) {
           return new ModelAndView("signin");
       }
       catch (NullIdCourseException ex) {
           ModelAndView mav = new ModelAndView("courseDetails/{course.id}");
           mav.addObject("excTitle", "Ooops...");
           mav.addObject("excMessage", ex.toString());
           return mav;
       }
        catch (IsNotOwnerException ex) {
            ModelAndView mav = new ModelAndView("courseDetails/{course.id}");
            mav.addObject("excTitle", "Ooops...No right, no action.");
            mav.addObject("excMessage", ex.toString());
            return mav;
        }




}


}
