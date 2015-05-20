package by.mogilev.controller;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
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

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by akiseleva on 03.04.2015.
 */
@Controller
public class MySeminarsController {
    public final static String MY_SEMINARS = "/mySeminars";

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;


    @ModelAttribute("Courses")
    public Course subscrCourse() {
        return new Course();
    }

    @RequestMapping(value = MY_SEMINARS, method = RequestMethod.GET)
    public ModelAndView listCourseUser(final HttpServletRequest request) throws NotFoundUserException {
        ModelAndView mav = new ModelAndView("mySeminars");

            String userName = userService.getUserFromSession(request);
            mav.addObject("nameCourses", courseService.getAllCourse());
            mav.addObject("courseList", userService.getCoursesSubscribersByUser(userName));
            mav.addObject("attCourseOfUser", userService.getCoursesAttendeeByUser(userName));
            return mav;

    }

    @RequestMapping(value = MY_SEMINARS, method = RequestMethod.POST)
    public ModelAndView mySem(@RequestParam(value = "grade", required = false) final Integer grade,
                              @RequestParam(value = "fieldForSubmit", required = false) final ActionsOnPage action,
                              @RequestParam(value = "selectCourse", required = false) final Integer id_course,
                              @RequestParam(value = "selectCategory", required = false) final String selectCategory,
                              final HttpServletRequest request, HttpServletResponse response)
            throws IOException, DocumentException, AddressException, NotFoundUserException, NotFoundCourseException {
        ModelAndView mav = new ModelAndView("mySeminars");
        String userName = userService.getUserFromSession(request);

          List<Course> courseList = userService.getCoursesSubscribersByUser(userService.getUserFromSession(request));
            if (courseList.size() == 0) {
                mav.addObject("modalTitle", "Ooops...");
                mav.addObject("modalMessage", "List is empty!");
                return mav;
            }

            switch (action) {
                case OUT_EXCEL: {
                    courseService.outInExcelAllCourse(response,courseList);
                    break;
                }
                case OUT_PDF: {
                    courseService.outInPdfAllCourse(response, courseList);
                    break;
                }
            }
            List<Course> coursesForList = userService.getCoursesSubscribersByUser(userName);
            mav.addObject("courseList", coursesForList);
            mav.addObject("nameCourses", courseService.getAllCourse());
            mav.addObject("attCourseOfUser", userService.getCoursesAttendeeByUser(userService.getUserFromSession(request)));
            return mav;




    }

}
