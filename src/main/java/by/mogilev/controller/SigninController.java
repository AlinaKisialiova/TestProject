package by.mogilev.controller;

/**
 * Created by akiseleva on 27.02.2015.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SigninController {
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin() {
        return "signin";
    }


    @RequestMapping(value = "/signin-failure", method = RequestMethod.GET)
    public String signinFailure() {
        return "signin_failure";
    }

}