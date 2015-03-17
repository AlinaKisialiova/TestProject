package by.mogilev.dao;

import by.mogilev.model.Course;
import by.mogilev.model.User;
import javassist.NotFoundException;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by akiseleva on 03.03.2015.
 */

public interface CourseDAO {

    public void deleteCourse(Course course);
    public List<Course> getAllCourse();
    public Course findCourse(int id);
    public void remidEv(int id, int grade);
    public void registerCourse(Course course, String nameLector ) ;
    public void updateCourse( Course course);
    boolean isOwner(int idCourse, HttpSession session);
    List<Course> getCoursesForUser();
    List<Course> getCoursesForLector();
    List<Course> getSelected(String category);

    Map<String, String> getCategotyMap();
}
