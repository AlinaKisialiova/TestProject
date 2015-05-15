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

    /**
     * Method set mark to course and initialize send email
     * @param id
     * @param user
     * @param grade
     * @throws AddressException
     * @throws NotFoundCourseException
     * @throws NotFoundUserException
     */
    public void remidEv(int id, User user, int grade) throws AddressException, NotFoundCourseException, NotFoundUserException;

    /**
     *Method return map with course category
     * @return
     */
    Map<String, String> getCategotyMap();
    /**
     *
     * Method out in pdf-file list of courses
     *
     */
    public void outInPdfAllCourse(HttpServletResponse response, List<Course> courses) throws IOException, DocumentException;
    /**
     *
     * Method out in Excel list of courses
     *
     */
    public void outInExcelAllCourse(HttpServletResponse response, List<Course> courses) throws IOException;
    /**
     *
     * Method selected on choose category
     *
     */
    List<Course> getSelected(String category);

    /**
     * Method return all course that registration in center
     * @return
     */
    public List<Course> getAllCourse();

    /**
     *  delete course
     * @param id
     * @param userName
     * @throws AddressException
     * @throws NotFoundCourseException
     * @throws NotFoundUserException
     * @throws IsNotOwnerException
     */
    public void deleteCourse(int id, String userName) throws AddressException, NotFoundCourseException, NotFoundUserException, IsNotOwnerException;

    /**
     * get course for id
     * @param id
     * @return
     * @throws NotFoundCourseException
     */
    public Course getCourse(int id) throws NotFoundCourseException;

    /**
     * register new course
     * @param course
     * @param nameLector
     * @throws AddressException
     * @throws NotFoundUserException
     */

    public void registerCourse(Course course, String nameLector) throws AddressException, NotFoundUserException, NotFoundCourseException;

    /**
     * update course
     * @param updCourse
     * @throws AddressException
     * @throws NotFoundCourseException
     */
    public void updateCourse(Course updCourse) throws AddressException, NotFoundCourseException, NotFoundUserException;

    /**
     * return course for approve department manager
     * @return
     */
    public List<Course> getCourseForDepartmentManager();

    /**
     * return course for approve knowledge manager
     * @return
     */
    public List<Course> getCourseForKnowledgeManagerManager();



}
