package by.mogilev.controller;

import by.mogilev.model.Course;
import by.mogilev.model.User;

import by.mogilev.service.CourseDAO;
import by.mogilev.service.CourseDAOImp;
import by.mogilev.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Created by akiseleva on 09.03.2015.
 */
@Controller
public class ActionCourseController {

    @Autowired
    @Qualifier("courseDAOImp")
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
    @RequestMapping(value = "/courseDetails/{course.id}", method = RequestMethod.GET)
    public ModelAndView detailsCourse(@PathVariable("course.id") Integer id)  {

        return  new ModelAndView("courseDetails", "checkCourse", course.findCourse(id) );
    }

    @RequestMapping(value = "/editCourse", method = RequestMethod.GET)
    public String editRegCourse() {
        return "editCourse";
    }

    @RequestMapping(value = "/editCourse", method = RequestMethod.POST)
    public String editCourse(HttpServletRequest request, Model model) {
        course.registerCourse(request.getParameter("updCourseCategory"), request.getParameter("updCourseName"),
                request.getParameter("updCourseDescription"), request.getParameter("updCourseLinks"),
                request.getParameter("updCourseDuration"));
        model.addAttribute("message", "Course Updated!");

        return "editCourse";
    }
}
