package by.mogilev.controller;

import by.mogilev.exception.TrainingCenterException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by akiseleva on 18.05.2015.
 */
@Controller
public class ExceptionController {
    public final static String ERROR = "/error";

    @RequestMapping(value = ERROR, method = RequestMethod.GET)
    public ModelAndView displayError(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        TrainingCenterException message = (TrainingCenterException) request.getAttribute("javax.servlet.error.exception");
        mav.addObject("message", message.toString());
        return mav;
    }
}
