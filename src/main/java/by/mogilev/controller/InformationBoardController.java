package by.mogilev.controller;

import by.mogilev.exception.IsNotOwnerException;
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
import java.security.Principal;


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
    public ModelAndView informBoardGET() {
        ModelAndView mav = new ModelAndView("informationBoard");
//        mav.addObject("modalTitle", "Ooops...");
//        mav.addObject("modalMessage","Все сломал наглый поросенок");
        mav.addObject("courseList", courseService.getAllCourse());
        return mav;
    }

    @RequestMapping(value = INFORM_BOARD, method = RequestMethod.POST)
    public ModelAndView informBoardPOST(HttpServletRequest request,
                                          @RequestParam(value = "grade", required = false) Integer grade,
                                          @RequestParam(value = "fieldForSubmit", required = false) ActionsOnPage action,
                                          @RequestParam(value = "id", required = false) Integer id_course,
                                          @RequestParam(value = "selectCategory", required = false) String selectCategory,
                                          HttpServletResponse response)
            throws IOException, DocumentException, AddressException, NotFoundUserException {
        ModelAndView mav = new ModelAndView("informationBoard");
        try {
            String userName = "";
            Principal principal = request.getUserPrincipal();
            if (principal != null && principal.getName() != null) {
                userName = principal.getName();
            } else throw new NotFoundUserException();

            if (action == null) action = ActionsOnPage.NO_ACTION;
            switch (action) {
                case DEL:
                    courseService.deleteCourse(id_course, userName);
                    break;
                case EVAL_REM:
                    User user = userService.getUser(userName);
                    courseService.remidEv(id_course, user, grade);
                    break;
                case OUT_PDF:
                    courseService.outInPdfAllCourse(response);
                    break;
                case OUT_EXCEL:
                    courseService.outInExcelAllCourse(response);
                    break;
                case START:
                    courseService.startCourse(id_course, userName);
                    break;
            }
            mav.addObject("courseList", courseService.getAllCourse());
        } catch (IsNotOwnerException e) {
            mav.addObject("modalTitle", "Ooops...");
            mav.addObject("modalMessage", e.toString());
            return new ModelAndView("courseDetails/{course.id}");
        } catch (NotFoundUserException ex) {
            return new ModelAndView("signin");
        } catch (NotFoundCourseException e) {
            mav.addObject("excTitle", "Ooops...");
            mav.addObject("excMessage", e.toString());
            return new ModelAndView("informationBoard");
        }

        return mav;

    }

}



