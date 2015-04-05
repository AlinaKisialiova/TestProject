package by.mogilev.controller;

import by.mogilev.dao.CourseDAO;
import by.mogilev.model.Course;
import by.mogilev.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by akiseleva on 09.03.2015.
 */
@Controller
public class ActionCourseController {

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

    @ModelAttribute("Course")
    public Course newCourse() {
        return new Course();
    }

    @RequestMapping(value = "/registrationCourse", method = RequestMethod.GET)
    public String displayRegCourse() {
        return "registrationCourse";
    }

    @RequestMapping(value = "/registrationCourse", method = RequestMethod.POST)
    public ModelAndView regCourse(@ModelAttribute("Course")  Course newCourse,  BindingResult result, Model model, HttpSession session) {
           if (result.hasErrors())
               new ModelAndView("redirect:/informationBoard");

            String userName = populateCurrentUser();

            courseService.registerCourse(newCourse, userName);
            return new ModelAndView("redirect:/informationBoard");

    }

    @RequestMapping(value = "/courseDetails/{course.id}", method = RequestMethod.GET)
    public ModelAndView detailsCourse(@PathVariable("course.id") Integer id) {
        ModelAndView mav= new ModelAndView("courseDetails");
        mav.addObject("checkCourse", courseService.getCourse(id));
        return mav;

    }

    @RequestMapping(value = "/editCourse/{course.id}", method = RequestMethod.GET)
    public String editRegCourse(@PathVariable("course.id") Integer id, Model model) {
        model.addAttribute("categoryMap", courseService.getCategotyMap());
        model.addAttribute("course", courseService.getCourse(id));
        return "editCourse";
    }

    @RequestMapping(value = "/editCourse/{course.id}", method = RequestMethod.POST)
    public String editCourse(@PathVariable("course.id") Integer id,
                             @ModelAttribute("Course") Course updCourse,
                             HttpServletRequest request, HttpSession session, Model model) {

        if (! (courseService.isOwner(id, session)) || courseService.getCourse(id) == null)
              return "redirect:/informationBoard";


        String p = request.getParameter("deleteCourse");

        if ("on".equals(p))
            courseService.deleteCourse(id);

        else {
            Course editCourse = courseService.getCourse(id);

            editCourse.setCategory(updCourse.getCategory());
            editCourse.setNameCourse(updCourse.getNameCourse());
            editCourse.setDescription(updCourse.getDescription());
            editCourse.setLinks(updCourse.getLinks());
            editCourse.setDuration(updCourse.getDuration());

            courseService.updateCourse(editCourse);
        }

          return "redirect:/informationBoard";
    }

    @ModelAttribute
    public String popCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = "";
        if (principal instanceof UserDetails) {
            userName = ((org.springframework.security.core.userdetails.User) principal).getUsername();
        }
        return userName;
    }


}
