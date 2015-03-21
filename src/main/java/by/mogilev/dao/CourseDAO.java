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
    public Course getCourse(int id);
    public void registerCourse(Course course, String nameLector ) ;
    public void updateCourse( Course course);
    List<Course> getSelected(String category);
    List<Course> getCoursesForUser();
    List<Course> getCoursesForLector();

}
