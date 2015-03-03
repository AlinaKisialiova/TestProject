package by.mogilev.controller;

import by.mogilev.model.User;
import by.mogilev.model.Course;
import by.mogilev.model.Participant;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akiseleva on 02.03.2015.
 */
@Controller
public class InformationBoardController {
    @Autowired
    private CourseActions course;

    @ModelAttribute
    public User populateCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
}

    @RequestMapping(value = "/informationBoard", method = RequestMethod.GET)
    public ModelAndView listCourse() {
        return new ModelAndView("informationBoard", "courseList", course.getAllCourse());

    }
}