package by.mogilev.controller;

import by.mogilev.exception.IsNotOwnerException;
import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.Course;
import by.mogilev.service.CourseService;
import by.mogilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by akiseleva on 09.03.2015.
 */
@Controller
public class ActionCourseController {
    public final static String REGISTRATION_COURSE = "/registrationCourse";
    public final static String DETAIL_COURSE = "/courseDetails/{course.id}";
    public final static String EDIT_COURSE = "/editCourse/{course.id}";

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @ModelAttribute("Course")
    public Course newCourse() {
        return new Course();
    }

    @RequestMapping(value = REGISTRATION_COURSE, method = RequestMethod.GET)
    public String displayRegCourse() {
        return "registrationCourse";
    }

    @RequestMapping(value = REGISTRATION_COURSE, method = RequestMethod.POST)
    public ModelAndView regCourse(@ModelAttribute("Course") Course newCourse, BindingResult result, Model model,
                                  HttpServletRequest request) throws NotFoundUserException, AddressException, NotFoundCourseException {

        String userName = userService.getUserFromSession(request);
        for (Course c : courseService.getAllCourse()) {
            if (c.getNameCourse().equals(newCourse.getNameCourse())) {
                ModelAndView mav = new ModelAndView("registrationCourse");
                mav.addObject("modalTitle", "Ooops...");
                mav.addObject("modalMessage", "Course with this name already exist!");
                return mav;
            }
        }
        courseService.registerCourse(newCourse, userName);
        return new ModelAndView("redirect:/informationBoard");

    }


    @RequestMapping(value = DETAIL_COURSE, method = RequestMethod.GET)
    public ModelAndView detailsCourse(@PathVariable("course.id") Integer id) throws NotFoundCourseException {
        ModelAndView mav = new ModelAndView("courseDetails");

            mav.addObject("checkCourse", courseService.getCourse(id));
            return mav;

    }


    @RequestMapping(value = DETAIL_COURSE, method = RequestMethod.POST)
    public ModelAndView deleteCourse(@PathVariable("course.id") Integer id, HttpServletRequest request) throws NotFoundUserException, NotFoundCourseException, AddressException, IsNotOwnerException {
        String userName = userService.getUserFromSession(request);

            courseService.deleteCourse(id, userName);
            return new ModelAndView("redirect:/informationBoard");
    }

    @RequestMapping(value = EDIT_COURSE, method = RequestMethod.GET)
    public String editRegCourse(@PathVariable("course.id") Integer id, Model model) throws NotFoundCourseException {

            model.addAttribute("categoryMap", courseService.getCategotyMap());
            model.addAttribute("course", courseService.getCourse(id));
            return "editCourse";

    }

    @RequestMapping(value = EDIT_COURSE, method = RequestMethod.POST)
    public ModelAndView editCourse(@PathVariable("course.id") final Integer id,
                             @ModelAttribute("Course") final Course updCourse,
                             final HttpServletRequest  request, final Model model) throws AddressException, NotFoundCourseException, NotFoundUserException, IsNotOwnerException {
        String userName = userService.getUserFromSession(request);

           model.addAttribute("categoryMap", courseService.getCategotyMap());
           if (!(courseService.isOwner(id, userName))) throw new IsNotOwnerException();
           Course editCourse = courseService.getCourse(id);
           editCourse.setCategory(updCourse.getCategory());
           editCourse.setNameCourse(updCourse.getNameCourse());
           editCourse.setDescription(updCourse.getDescription());
           editCourse.setLinks(updCourse.getLinks());
           editCourse.setDuration(updCourse.getDuration());

           courseService.updateCourse(editCourse);
           return new ModelAndView("redirect:/courseDetails/{course.id}");


}


}
