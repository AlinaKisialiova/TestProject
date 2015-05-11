package by.mogilev.controller;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.ActionsOnPage;
import by.mogilev.model.Course;
import by.mogilev.model.User;
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

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by akiseleva on 03.04.2015.
 */
@Controller
public class MySeminarsController {
    public final String MY_SEMINARS = "/mySeminars";


    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;


    @ModelAttribute("Courses")
    public Course subscrCourse() {
        return new Course();
    }

    @RequestMapping(value = MY_SEMINARS, method = RequestMethod.GET)
    public ModelAndView listCourseUser(HttpServletRequest request) throws NotFoundUserException {
        ModelAndView mav = new ModelAndView("mySeminars");
        try {
            String userName = userService.getUserFromSession(request);

            mav.addObject("nameCourses", courseService.getAllCourse());

            mav.addObject("courseList", userService.getCoursesSubscribeOfUser(userName));
            mav.addObject("attCourseOfUser", userService.getCoursesAttendeeOfUser(userName));
            return mav;
        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");
        }
    }

    @RequestMapping(value = MY_SEMINARS, method = RequestMethod.POST)
    public ModelAndView mySem(@RequestParam(value = "grade", required = false) Integer grade,
                              @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action,
                              @RequestParam(value = "selectCourse", required = false) Integer id_course,
                              @RequestParam(value = "selectCategory", required = false) String selectCategory,
                              @RequestParam(value = "id", required = false) Integer id,
                              HttpServletRequest request)
            throws IOException, DocumentException, AddressException, NotFoundUserException {
        ModelAndView mav = new ModelAndView("mySeminars");
try {
        List<Course> coursesForSelect = courseService.getSelected(selectCategory);
        mav.addObject("nameCourses", coursesForSelect);

        User user = userService.getUser(userService.getUserFromSession(request));
        if (ActionsOnPage.EVAL_REM.equals(action))
            courseService.remidEv(id_course, user, grade);

        Set<Course> coursesForList = userService.getCoursesSubscribeOfUser(userService.getUserFromSession(request));
        mav.addObject("courseList", coursesForList);

        mav.addObject("attCourseOfUser", userService.getCoursesAttendeeOfUser(userService.getUserFromSession(request)));
        return mav;
    } catch (NotFoundUserException ex) {
        return new ModelAndView("signin");
    } catch (NotFoundCourseException e) {
    mav.addObject("modalTitle", "Ooops...");
    mav.addObject("modalMessage", e.toString());
    return mav;
}

    }

    }
