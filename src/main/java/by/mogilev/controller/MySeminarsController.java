package by.mogilev.controller;

import by.mogilev.model.ActionsOnPage;
import by.mogilev.model.Course;
import by.mogilev.service.CourseService;
import by.mogilev.service.UserService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Created by akiseleva on 03.04.2015.
 */
@Controller
public class MySeminarsController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;


    @ModelAttribute("Courses")
    public Course subscrCourse() {
        return new Course();
    }

    @RequestMapping(value = "/mySeminars", method = RequestMethod.GET)
    public ModelAndView listCourseUser(HttpServletRequest request ) {
        ModelAndView mav = new ModelAndView("mySeminars");

        String userName = "";
        Principal principal = request.getUserPrincipal();
        if (principal != null && principal.getName() != null) {
            userName = principal.getName();
        }
        int id = userService.getIdByUsername(userName);

        mav.addObject("nameCourses", courseService.getAllCourse());

        mav.addObject("courseList", userService.getCoursesSubscribeOfUser(id));
        mav.addObject("attCourseOfUser", userService.getCoursesAttendeeOfUser(id));



        return mav;
    }

    @RequestMapping(value = "/mySeminars", method = RequestMethod.POST)
    public ModelAndView mySem(@RequestParam(value = "grade", required = false) Integer grade,
                                          @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action,
                                          @RequestParam(value = "selectCourse", required = false) Integer id_course,
                                          @RequestParam(value = "selectCategory", required = false) String selectCategory,
                                          @RequestParam(value = "id", required = false) Integer id,
                                          Principal principal)
            throws IOException, DocumentException {
        ModelAndView mav = new ModelAndView("mySeminars");

        List<Course> coursesForSelect =courseService.getSelected(selectCategory);
        mav.addObject("nameCourses", coursesForSelect );



        if (ActionsOnPage.EVAL_REM.equals(action))
            courseService.remidEv(id_course, grade);

        if (ActionsOnPage.ADD_IN_ATT.equals(action)|| ActionsOnPage.REMOTE_FROM_ATT.equals(action)) {
           String message= userService.addInAttSet(principal.getName(), id);
            mav.addObject("attendersMessage", message);
        }


        if (ActionsOnPage.SUBSCRIBE.equals(action)) {
            String message=userService.addInSubscribers(principal.getName(), id_course);
            mav.addObject("subscribeMessage", message);
        }


        int id_user=userService.getIdByUsername(principal.getName());
        Set<Course> coursesForList=userService.getCoursesSubscribeOfUser(id_user);
        mav.addObject("courseList", coursesForList);
        mav.addObject("attCourseOfUser", userService.getCoursesAttendeeOfUser(id_user));
        return mav;

    }

}
