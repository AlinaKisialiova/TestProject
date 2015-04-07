package by.mogilev.controller;

import by.mogilev.model.ActionsOnPage;
import by.mogilev.service.CourseService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by akiseleva on 02.03.2015.
 */

@Controller
public class InformationBoardController {


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

    @RequestMapping(value = "/informationBoard", method = RequestMethod.GET)
    public ModelAndView listCourse() {
       ModelAndView mav= new ModelAndView("informationBoard");
        mav.addObject("courseList", courseService.getAllCourse());

        return mav;
    }

    @RequestMapping(value = "/informationBoard", method = RequestMethod.POST)
    public ModelAndView evRemindAndDelete(@RequestParam(value = "grade", required = false) Integer grade,
                                          @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action,
                                          @RequestParam(value = "id", required = false) Integer id,
                                          @RequestParam(value = "selectCategory", required = false) String selectCategory,
                                          HttpServletResponse response)
            throws IOException, DocumentException {
        ModelAndView mav = new ModelAndView("informationBoard");
        if (ActionsOnPage.DEL.equals(action))
            courseService.deleteCourse(id);

        if (ActionsOnPage.EVAL_REM.equals(action))
            courseService.remidEv(id, grade);

        if (ActionsOnPage.OUT_PDF.equals(action))
            courseService.outInPdfAllCourse(response);

        if (ActionsOnPage.OUT_EXCEL.equals(action))
            courseService.outInExcelAllCourse(response);

        if (ActionsOnPage.START.equals(action))
            courseService.startCourse(id);

        mav.addObject("courseList", courseService.getSelected(selectCategory));

        return mav;

    }
}


