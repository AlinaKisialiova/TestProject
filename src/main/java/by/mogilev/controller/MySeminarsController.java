package by.mogilev.controller;

import by.mogilev.dao.CourseDAO;
import by.mogilev.service.CourseService;
import by.mogilev.service.UserService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by akiseleva on 03.04.2015.
 */
@Controller
public class MySeminarsController {

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/mySeminars", method = RequestMethod.GET)
    public ModelAndView listCourseUser(HttpServletRequest request) {
        ModelAndView mav= new ModelAndView("mySeminars");

        String userName="";
        Principal principal = request.getUserPrincipal();
        if (principal != null && principal.getName()!=null) {
            userName = principal.getName();
        }
        int id=userService.getIdByUsername(userName);

        mav.addObject("courseList", userService.getCoursesSubscribeOfUser(id));
        mav.addObject("attCourseOfUser", userService.getCoursesAttendeeOfUser(id));

        return mav;
    }

    @RequestMapping(value = "/mySeminars", method = RequestMethod.POST)
    public ModelAndView evRemindAndDelete(@RequestParam(value = "grade", required = false) Integer grade,
                                          @RequestParam(value = "fieldForSubmit", required = false) String action,
                                          @RequestParam(value = "id", required = false) Integer id,
                                          @RequestParam(value = "selectCategory", required = false) String selectCategory,
                                          HttpServletResponse response)
            throws IOException, DocumentException {


        if ("evalRem".equals(action))
            courseService.remidEv(id, grade);

        return new ModelAndView("mySeminars").
                addObject("courseList",userService.getCoursesSubscribeOfUser(id));
    }

}
