package by.mogilev.controller;

/**
 * Created by akiseleva on 27.02.2015.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SigninController {
    public final static String SIGNIN = "/signin";
    public final static String FAIL = "/signin-failure";
    public final static String DENIED = "/deniedPage";


    @RequestMapping(value = SIGNIN, method = RequestMethod.GET)
    public String signin() {
        return "signin";
    }


    @RequestMapping(value = FAIL, method = RequestMethod.GET)
    public String signinFailure() {
        return "signin_failure";
    }

    @RequestMapping(value = DENIED, method = RequestMethod.GET)
    public String deniedPage() {
        return "deniedPage";
    }


}