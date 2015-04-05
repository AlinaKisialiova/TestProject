package by.mogilev.controller;

import by.mogilev.dao.CourseDAO;
import by.mogilev.dao.UserDAO;
import by.mogilev.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Администратор on 29.03.2015.
 */
@Controller
public class ParticipantController {
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/participantsList/{course.id}", method = RequestMethod.GET)
    public ModelAndView participantsList(@PathVariable("course.id") Integer id) {
        ModelAndView mav= new ModelAndView("participantsList");
        mav.addObject("checkCourse", courseService.getCourse(id));
        return mav;
    }
}