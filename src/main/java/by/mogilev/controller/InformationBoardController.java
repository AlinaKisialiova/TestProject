package by.mogilev.controller;

import by.mogilev.model.User;
import by.mogilev.model.Course;

import by.mogilev.model.User;
import by.mogilev.service.CourseActions;
import by.mogilev.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akiseleva on 02.03.2015.
 */
@Controller
public class InformationBoardController {
    public static final String EDIT_ID = "id";
    @Autowired
    private CourseActions course;

    @ModelAttribute
    public User populateCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @RequestMapping(value = "/informationBoard", method = RequestMethod.GET)
    public ModelAndView listCourse() {
        return new ModelAndView("informationBoard", "courseList", course.getAllCourse());
    }

    @RequestMapping(value = "/informationBoard", method = RequestMethod.POST)
    public ModelAndView evRemind( @RequestParam(value = EDIT_ID, required = false) Integer id) {

        ModelAndView mav = new ModelAndView("informationBoard", "courseList", course.getAllCourse());
        if (id!= null) {
            Course findCourse = course.findCourse(id);
            mav.addObject("lectorName", findCourse.getNameLector());
            mav.addObject("courseName", findCourse.getNameCourse());
        }

        return mav;

    }
}


