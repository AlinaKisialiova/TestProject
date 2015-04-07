package by.mogilev.service;

import by.mogilev.model.Course;
import com.itextpdf.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Администратор on 21.03.2015.
 */
public interface CourseService {
   public boolean isOwner(int idCourse, HttpSession session);

    public void remidEv(int id, int grade);
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

    public void deleteCourse(int id);

    public Course getCourse(int id);

    public void registerCourse(Course course, String nameLector);

    public void updateCourse( Course course);

    public boolean startCourse(int id);
}
