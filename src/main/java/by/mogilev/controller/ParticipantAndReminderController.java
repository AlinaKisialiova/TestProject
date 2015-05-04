package by.mogilev.controller;

import by.mogilev.model.User;
import by.mogilev.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Администратор on 29.03.2015.
 */
@Controller
public class ParticipantAndReminderController {
    private final String PARTICIPANT_LIST ="/participantsList/{course.id}";
    @Autowired
    private CourseService courseService;

    @RequestMapping(value = PARTICIPANT_LIST, method = RequestMethod.GET)
    public ModelAndView participantsListGET(@PathVariable("course.id") Integer id) {
        ModelAndView mav= new ModelAndView("participantsList");
        mav.addObject("checkCourse", courseService.getCourse(id));
        return mav;
    }

    @RequestMapping(value = PARTICIPANT_LIST, method = RequestMethod.POST)
    public ModelAndView participantsListPOST(@PathVariable("course.id") Integer id,
                                             @RequestParam(value = "selectParticipants", required = false) String select) {
        ModelAndView mav= new ModelAndView("participantsList");
        Set<User> participants = new HashSet<User>();
        if ("All Participants".equals(select))
            participants = courseService.getCourse(id).getSubscribers();
        else
        if ("Current Attendees".equals(select))
            participants = courseService.getCourse(id).getAttenders();

        mav.addObject("checkCourse", courseService.getCourse(id));
        mav.addObject("participants", participants);
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
