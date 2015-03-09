package by.mogilev.controller;

import by.mogilev.model.User;
import by.mogilev.service.CourseActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;

/**
 * Created by akiseleva on 09.03.2015.
 */
@Controller
public class ActionCourseController {
    @Autowired
    private CourseActions course;

    @ModelAttribute
    public User populateCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping(value = "/registrationCourse", method = RequestMethod.GET)
    public String displayRegCourse() {
        return "registrationCourse";
    }

    @RequestMapping(value = "/registrationCourse", method = RequestMethod.POST)
    public String regCourse(HttpServletRequest request, Model model) {

            course.registerCourse(request.getParameter("newCourseCategory"), request.getParameter("newCourseName"),
                    request.getParameter("newCourseDescription"), request.getParameter("newCourseLinks"),
                    request.getParameter("newCourseDuration"));
        model.addAttribute("message", "Course added!");

        return "informationBoard";
    }
}
