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
    public ModelAndView regCourse(HttpServletRequest request, Model model) {

        course.registerCourse(request.getParameter("newCourseCategory"), request.getParameter("newCourseName"),
                request.getParameter("newCourseDescription"), request.getParameter("newCourseLinks"),
                request.getParameter("newCourseDuration"), request.getParameter("lectorName"));
        model.addAttribute("message", "Course added!");

        return new ModelAndView("informationBoard", "courseList", course.getAllCourse());
    }

    @RequestMapping(value = "/courseDetails/{course.id}", method = RequestMethod.GET)
    public ModelAndView detailsCourse(@PathVariable("course.id") Integer id) {
        return new ModelAndView("courseDetails", "checkCourse", course.findCourse(id));
    }

    @RequestMapping(value = "/editCourse/{course.id}", method = RequestMethod.GET)
    public ModelAndView editRegCourse(@PathVariable("course.id") Integer id) {
        return new ModelAndView("editCourse", "checkCourse", course.findCourse(id));
    }

    @RequestMapping(value = "/editCourse/{course.id}", method = RequestMethod.POST)
    public String editCourse(@PathVariable("course.id") Integer id,
                             HttpServletRequest request, Model model) {
        String p = request.getParameter("deleteCourse");
        if ("on".equals(p)) {
            Course delCourse = course.findCourse(id);
            course.deleteCourse(delCourse);
            model.addAttribute("message", "Course Deleted!");

        }
        else {
            course.updateCourse(id,request.getParameter("updCourseCategory"), request.getParameter("updCourseName"),
                    request.getParameter("updCourseDescription"), request.getParameter("updCourseLinks"),
                    request.getParameter("updCourseDuration"));
            model.addAttribute("message", "Course Updated!");
                }
        return "editCourse";
    }

}
