package by.mogilev.controller;

import by.mogilev.model.Course;

import by.mogilev.dao.CourseDAO;
import by.mogilev.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by akiseleva on 09.03.2015.
 */
@Controller
public class ActionCourseController {

    @Autowired
    private CourseDAO course;

    @ModelAttribute
    public String populateCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = "";
        if (principal instanceof UserDetails) {
            userName = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        }
        return userName;
    }

    @ModelAttribute("Course")
    public Course newCourse() {
        return new Course();
    }

    @RequestMapping(value = "/registrationCourse", method = RequestMethod.GET)
    public String displayRegCourse() {
        return "registrationCourse";
    }

    @RequestMapping(value = "/registrationCourse", method = RequestMethod.POST)
    public ModelAndView regCourse(@ModelAttribute("Course")  Course newCourse,  BindingResult result, Model model, HttpSession session) {
           if (!result.hasErrors()) new ModelAndView("redirect:/informationBoard");
            String userName = populateCurrentUser();

            course.registerCourse(newCourse, userName);
            return new ModelAndView("redirect:/informationBoard");

    }

    @RequestMapping(value = "/courseDetails/{course.id}", method = RequestMethod.GET)
    public ModelAndView detailsCourse(@PathVariable("course.id") Integer id) {
        return new ModelAndView("courseDetails")
                .addObject("checkCourse", course.findCourse(id));
    }

    @RequestMapping(value = "/editCourse/{course.id}", method = RequestMethod.GET)
    public String editRegCourse(@PathVariable("course.id") Integer id, Model model) {
        model.addAttribute("course",course.findCourse(id));
        return "editCourse";
    }

    @RequestMapping(value = "/editCourse/{course.id}", method = RequestMethod.POST)
    public String editCourse(@PathVariable("course.id") Integer id,
                             @ModelAttribute("Course") Course updCourse,
                             HttpServletRequest request, HttpSession session, Model model) {

        if (course.findCourse(id) == null) return "redirect:/informationBoard";

        if (course.isOwner(id, session)) {

        String p = request.getParameter("deleteCourse");
        if ("on".equals(p)) {
            Course delCourse = course.findCourse(id);
            course.deleteCourse(delCourse);
        }
        else
            course.updateCourse(updCourse);
            }
        else
            model.addAttribute("message", "Forbidden update");

        return "redirect:/informationBoard";

    }

}
