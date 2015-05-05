package by.mogilev.controller;

import by.mogilev.exception.NullIdCourseException;
import by.mogilev.exception.NullUserException;
import by.mogilev.model.ActionsOnPage;
import by.mogilev.model.Course;
import by.mogilev.model.User;
import by.mogilev.service.CourseService;
import by.mogilev.service.UserService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
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
    public final String MY_SEMINARS = "/mySeminars";
    public final String ATTENDEE_LIST = "/attendeeList/{course.id}";

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;


    @ModelAttribute("Courses")
    public Course subscrCourse() {
        return new Course();
    }

    @RequestMapping(value = MY_SEMINARS, method = RequestMethod.GET)
    public ModelAndView listCourseUser(HttpServletRequest request) throws NullUserException {
        ModelAndView mav = new ModelAndView("mySeminars");
        try {
            String userName = userService.getUserFromSession(request);

            mav.addObject("nameCourses", courseService.getAllCourse());

            mav.addObject("courseList", userService.getCoursesSubscribeOfUser(userName));
            mav.addObject("attCourseOfUser", userService.getCoursesAttendeeOfUser(userName));
            return mav;
        } catch (NullUserException ex) {
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
            throws IOException, DocumentException, AddressException, NullUserException {
        ModelAndView mav = new ModelAndView("mySeminars");
try {
        List<Course> coursesForSelect = courseService.getSelected(selectCategory);
        mav.addObject("nameCourses", coursesForSelect);

        User user = userService.getUser(userService.getUserFromSession(request));
        if (ActionsOnPage.EVAL_REM.equals(action))
            courseService.remidEv(id_course, user, grade);

        if (ActionsOnPage.SUBSCRIBE.equals(action)) {
            String message = "";
            if (userService.addInSubscribers(userService.getUserFromSession(request), id_course))
                message = "You are subscribed!";
            else
                message = "You are deleted from subscribe list!!";

            mav.addObject("subscribeMessage", message);
        }


        Set<Course> coursesForList = userService.getCoursesSubscribeOfUser(userService.getUserFromSession(request));
        mav.addObject("courseList", coursesForList);

        mav.addObject("attCourseOfUser", userService.getCoursesAttendeeOfUser(userService.getUserFromSession(request)));
        return mav;
    } catch (NullUserException ex) {
        return new ModelAndView("signin");
    } catch (NullIdCourseException e) {
    mav.addObject("excTitle", "Ooops...");
    mav.addObject("excMessage", e.toString());
    return mav;
}

    }
    @RequestMapping(value = ATTENDEE_LIST, method = RequestMethod.GET)
    public ModelAndView AttendeeListGET(@PathVariable("course.id") Integer id) throws NullIdCourseException {
        ModelAndView mav = new ModelAndView("attendeeList");
        try {
            mav.addObject("checkCourse", courseService.getCourse(id));
            mav.addObject("attendee", courseService.getCourse(id).getAttenders());
            return mav;

        } catch (NullIdCourseException e) {
            ModelAndView mavExc = new ModelAndView("informationBoard");
            mavExc.addObject("excTitle", "Ooops...");
            mavExc.addObject("excMessage", e.toString());
            return mavExc;
        }
    }

    @RequestMapping(value = ATTENDEE_LIST, method = RequestMethod.POST)
    public ModelAndView AttendeeListPOST(@PathVariable("course.id") Integer id,
                                         Principal principal, @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action)
            throws Exception {
        ModelAndView mav = new ModelAndView("attendeeList");
        try {
            mav.addObject("checkCourse", courseService.getCourse(id));
            String message = "";
            switch (action) {
                case ADD_IN_ATT:
                    userService.addInAttSet(principal.getName(), id);
                    message = "You are included in attenders list!";
                    break;

                case REMOTE_FROM_ATT:
                    userService.removeFromAttSet(principal.getName(), id);
                    message = "You are deleted from attenders list!";
                    break;
            }
            mav.addObject("attendersMessage", message);
            mav.addObject("attendee", courseService.getCourse(id).getAttenders());

            return mav;

    }catch (NullIdCourseException e) {

        mav.addObject("excTitle", "Ooops...");
        mav.addObject("excMessage", e.toString());
        return mav;
    }

}
    }
