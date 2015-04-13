package by.mogilev.controller;

import by.mogilev.service.CourseService;
import by.mogilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by akiseleva on 13.04.2015.
 */
@Controller
public class ApproveCourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/approveCourse/{course.id}", method = RequestMethod.GET)
    public ModelAndView approveCourseGet(@PathVariable("course.id") Integer id) {
        ModelAndView mav= new ModelAndView("approveCourse");
        mav.addObject("courseList", courseService.getCourse(id));
        return mav;
    }
}
