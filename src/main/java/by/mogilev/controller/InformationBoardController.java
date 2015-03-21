package by.mogilev.controller;

import by.mogilev.dao.CourseDAO;
import by.mogilev.model.Course;
import by.mogilev.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;


/**
 * Created by akiseleva on 02.03.2015.
 */

@Controller
public class InformationBoardController {

    @Autowired
    @Qualifier("courseDAOImp")
    private CourseDAO course;

    @Autowired
    private CourseService courseService;

    @ModelAttribute
    public String populateCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = "";
        if (principal instanceof UserDetails) {
            userName = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        }
        return userName;
    }

    @RequestMapping(value = "/informationBoard", method = RequestMethod.GET)
    public ModelAndView listCourse() {
        return new ModelAndView("informationBoard", "courseList", course.getAllCourse());
    }

    @RequestMapping(value = "/informationBoard", method = RequestMethod.POST)
    public ModelAndView evRemindAndDelete(@RequestParam(value = "grade", required = false) Integer grade,
                                          @RequestParam(value = "fieldForSubmit", required = false) String action,
                                          @RequestParam(value = "id", required = false) Integer id) {

        if ("del".equals(action))
            course.deleteCourse(course.getCourse(id));

        if ("evalRem".equals(action))
            courseService.remidEv(id, grade);

        return new ModelAndView("informationBoard", "courseList", course.getAllCourse());

    }
}


