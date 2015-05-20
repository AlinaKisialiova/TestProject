package by.mogilev.controller;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.ActionsOnPage;
import by.mogilev.model.User;
import by.mogilev.service.CourseService;
import by.mogilev.service.UserService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


/**
 * Created by akiseleva on 02.03.2015.
 */

@Controller
public class InformationBoardController {
    public final static String INFORM_BOARD = "/informationBoard";

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = INFORM_BOARD, method = RequestMethod.GET)
    public ModelAndView informBoardGet() {
        ModelAndView mav = new ModelAndView("informationBoard");
        mav.addObject("courseList", courseService.getAllCourse());
        return mav;
    }

    @RequestMapping(value = INFORM_BOARD, method = RequestMethod.POST)
    public ModelAndView informBoardPost(HttpServletRequest request,
                                          @RequestParam(value = "grade", required = false) final Integer grade,
                                          @RequestParam(value = "fieldForSubmit", required = false)  ActionsOnPage action,
                                          @RequestParam(value = "id", required = false) final Integer id_course,
                                          HttpServletResponse response)
            throws IOException, DocumentException, NotFoundUserException, NotFoundCourseException, AddressException {
        ModelAndView mav = new ModelAndView("informationBoard");
        String userName = userService.getUserFromSession(request);


            if (action == null) action = ActionsOnPage.NO_ACTION;

            switch (action) {
                    case EVAL_REM:
                    User user = userService.getUser(userName);
                    Set<User> attenders = courseService.getCourse(id_course).getAttenders();
                    if (attenders.contains(user))
                    courseService.remidEv(id_course, user, grade);
                    else {
                        mav.addObject("modalTitle", "Ooops...");
                        mav.addObject("modalMessage", "You don't put make because you don't attendee!");
                    }
                    break;
                case OUT_PDF:
                    courseService.outInPdfAllCourse(response, courseService.getAllCourse());
                    break;
                case OUT_EXCEL:
                    courseService.outInExcelAllCourse(response, courseService.getAllCourse());
                    break;

            }
            mav.addObject("courseList", courseService.getAllCourse());
            return mav;

            }

}



