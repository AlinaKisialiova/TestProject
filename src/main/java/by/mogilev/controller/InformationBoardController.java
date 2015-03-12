package by.mogilev.controller;

import by.mogilev.model.Course;
import by.mogilev.service.CourseDAO;
import by.mogilev.service.CourseDAOImp;
import by.mogilev.service.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;


/**
 * Created by akiseleva on 02.03.2015.
 */

@Controller
public class InformationBoardController {

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


    @RequestMapping(value = "/informationBoard", method = RequestMethod.GET)
    public ModelAndView listCourse() throws SQLException{
        return new ModelAndView("informationBoard", "courseList", course.getAllCourse());
    }

    @RequestMapping(value = "/informationBoard", method = RequestMethod.POST)
    public ModelAndView evRemind(@RequestParam(value = "grade", required = false) Integer grade,
                                 @RequestParam(value = "id", required = false) Integer id) throws SQLException{

        Course changeEvalCourse= course.findCourse(id);
       changeEvalCourse.setEvaluation(grade);
        return new ModelAndView("informationBoard", "courseList", course.getAllCourse());

    }
}


