package by.mogilev.service;

import by.mogilev.exception.IsNotOwnerException;
import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.Course;
import by.mogilev.model.User;
import com.itextpdf.text.DocumentException;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Администратор on 21.03.2015.
 */
public interface CourseService {
    /**
     * Method validate who create this course
     * @param idCourse
     * @param UserName
     * @return
     */
   public boolean isOwner(int idCourse, String UserName) throws NotFoundUserException, NotFoundCourseException;

    public void remidEv(int id, User user, int grade) throws AddressException, NotFoundCourseException, NotFoundUserException;
    Map<String, String> getCategotyMap();
    /**
     *
     * Method out in pdf-file list of courses
     *
     */
    public void outInPdfAllCourse(HttpServletResponse response) throws IOException, DocumentException;
    /**
     *
     * Method out in Excel list of courses
     *
     */
    public void outInExcelAllCourse(HttpServletResponse response) throws IOException;
    /**
     *
     * Method selected on choose category
     *
     */
    List<Course> getSelected(String category);
    /**
     *
     * Method add user in set of attenders
     *
     */


    public List<Course> getAllCourse();

    public void deleteCourse(int id, String userName) throws AddressException, NotFoundCourseException, NotFoundUserException, IsNotOwnerException;

    public Course getCourse(int id) throws NotFoundCourseException;

    public void registerCourse(Course course, String nameLector) throws AddressException, NotFoundUserException;

    public void updateCourse(Course updCourse) throws AddressException, NotFoundCourseException;

    public boolean startCourse(int id, String userName);

    public Course getCourseByName(String courseName);




}
