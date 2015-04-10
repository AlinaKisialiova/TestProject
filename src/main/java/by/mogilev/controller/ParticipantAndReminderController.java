package by.mogilev.controller;

import by.mogilev.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by Администратор on 29.03.2015.
 */
@Controller
public class ParticipantAndReminderController {
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/participantsList/{course.id}", method = RequestMethod.GET)
    public ModelAndView participantsList(@PathVariable("course.id") Integer id) {
        ModelAndView mav= new ModelAndView("participantsList");
        mav.addObject("checkCourse", courseService.getCourse(id));
        return mav;
    }

    @RequestMapping(value = "/evaluationReminder/{course.id}", method = RequestMethod.GET)
    public ModelAndView reminder(@PathVariable("course.id") Integer id) {
        ModelAndView mav= new ModelAndView("evaluationReminder");
        mav.addObject("checkCourse", courseService.getCourse(id));
        return mav;
    }


    @RequestMapping(value = "/evaluationReminder/{course.id}", method = RequestMethod.POST)
    public ModelAndView reminderStartorReset(@PathVariable("course.id") Integer id, HttpServletRequest request) {
        ModelAndView mav= new ModelAndView("evaluationReminder");
        mav.addObject("checkCourse", courseService.getCourse(id));
        String userName = "";
        Principal principal = request.getUserPrincipal();
        if (principal != null && principal.getName() != null) {
            userName = principal.getName();
        }
        String message="";
        if (courseService.startCourse(id, userName))
            message="Course "+ courseService.getCourse(id).getNameCourse()+" is started!";
        else
        message="Course dont started!";

        mav.addObject("startMessage", message);
        return mav;
    }
}
