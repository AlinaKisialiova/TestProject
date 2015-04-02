package by.mogilev.controller;

import by.mogilev.dao.CourseDAO;
import by.mogilev.model.Course;
import by.mogilev.service.CourseService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by akiseleva on 02.03.2015.
 */

@Controller
public class InformationBoardController {

    @Autowired
    private CourseDAO courseDAO;

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
        return new ModelAndView("informationBoard", "courseList", courseDAO.getAllCourse());
    }

    @RequestMapping(value = "/informationBoard", method = RequestMethod.POST)
    public ModelAndView evRemindAndDelete(@RequestParam(value = "grade", required = false) Integer grade,
                                          @RequestParam(value = "fieldForSubmit", required = false) String action,
                                          @RequestParam(value = "id", required = false) Integer id,
                                          @RequestParam(value = "selectCategory", required = false) String selectCategory,
                                          HttpServletResponse response)
            throws IOException, DocumentException {

        if ("del".equals(action))
            courseDAO.deleteCourse(courseDAO.getCourse(id));
        Course course;


        if ("evalRem".equals(action))
            courseService.remidEv(id, grade);

        if ("outPdf".equals(action))
            courseService.outInPdfAllCourse(response);

        if ("outExcel".equals(action))
            courseService.outInExcelAllCourse(response);



        return new ModelAndView("informationBoard").
                addObject("courseList", courseDAO.getSelected(selectCategory));
    }
}


