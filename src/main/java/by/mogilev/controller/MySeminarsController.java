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

        mav.addObject("nameCourses", courseService.getAllCourse());

        mav.addObject("courseList", userService.getCoursesSubscribeOfUser(userName));
        mav.addObject("attCourseOfUser", userService.getCoursesAttendeeOfUser(userName));



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

        List<Course> coursesForSelect = courseService.getSelected(selectCategory);
        mav.addObject("nameCourses", coursesForSelect );


        if (ActionsOnPage.EVAL_REM.equals(action))
            courseService.remidEv(id_course, principal.getName(), grade);

        if (ActionsOnPage.ADD_IN_ATT.equals(action)|| ActionsOnPage.REMOTE_FROM_ATT.equals(action)) {

           String message= "";
        if (courseService.getCourse(id).getAttenders().size() < Course.MAX_COUNT_ATT) {

            if (userService.addInAttSet(principal.getName(), id))
                message = "You are included in attenders list!";
            else
                message = "You are deleted from attenders list!";
        }
            else
            message="You can not inclused because a group recruited!";

            mav.addObject("attendersMessage", message);
        }


        if (ActionsOnPage.SUBSCRIBE.equals(action)) {
            String message="";
            if(userService.addInSubscribers(principal.getName(), id_course))
             message = "You are subscribed!";
            else
            message = "You are already subscribed to this course!";

            mav.addObject("subscribeMessage", message);
        }


        Set<Course> coursesForList = userService.getCoursesSubscribeOfUser(principal.getName());
        mav.addObject("courseList", coursesForList);

        mav.addObject("attCourseOfUser", userService.getCoursesAttendeeOfUser(principal.getName()));
        return mav;

    }

}
