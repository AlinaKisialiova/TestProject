package by.mogilev.controller;

import by.mogilev.model.Course;
import by.mogilev.model.CourseStatus;
import by.mogilev.model.UserRole;
import by.mogilev.service.CourseService;
import by.mogilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by akiseleva on 13.04.2015.
 */
@Controller
public class ApproveCourseController {
    private final String APPROVE_COURSE_GET = "/approveCourse/{course.id}";
    private final String APPROVE_COURSE_POST = "/approveCourse/{course.id}";

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = APPROVE_COURSE_GET, method = RequestMethod.GET)
    public ModelAndView approveCourseGet(@PathVariable("course.id") Integer id) {
        ModelAndView mav= new ModelAndView("approveCourse");
        mav.addObject("courseList", courseService.getCourse(id));
        return mav;
    }

    @RequestMapping(value = APPROVE_COURSE_POST, method = RequestMethod.POST)
    public ModelAndView approveCoursePost(@PathVariable("course.id") Integer id, @RequestParam("approveToServ") String approve,
                                          @RequestParam("reasonToServ") String reason, @RequestParam("manager") UserRole manager) {

        Course appCourse = courseService.getCourse(id);
        switch (manager) {
            case DEPARTMENT_MANAGER :
                if ("approve".equals(approve))
                appCourse.setCourseStatus(CourseStatus.APPROVE_DEPARTMENT_MANAGER);
                else
                    appCourse.setCourseStatus(CourseStatus.NOT_APPROVE);
                appCourse.setDepartmentManagerReason(reason);
                break;

            case KNOWLEDGE_MANAGER:
                if ("approve".equals(approve))
                appCourse.setCourseStatus(CourseStatus.APPROVE_KNOWLEDGE_MANAGER);
                else
                appCourse.setCourseStatus(CourseStatus.NOT_APPROVE);

                appCourse.setKnowledgeManagerReason(reason);
                break;
        }

        courseService.updateCourse(appCourse);

        ModelAndView mav= new ModelAndView("informationBoard");
        mav.addObject("courseList", courseService.getAllCourse());
        return mav;
    }
}
