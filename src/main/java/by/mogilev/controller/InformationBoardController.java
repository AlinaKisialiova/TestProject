package by.mogilev.controller;

//import by.mogilev.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by akiseleva on 02.03.2015.
 */
@Controller
public class InformationBoardController {
//    @ModelAttribute
//    public User populateCurrentUser(){
//        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }
    @RequestMapping(value = "/informationBoard", method = RequestMethod.GET)
    public String goInfBoard() {
        return "informationBoard";
    }
}
