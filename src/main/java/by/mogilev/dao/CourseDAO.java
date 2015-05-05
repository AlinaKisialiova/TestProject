package by.mogilev.dao;

import by.mogilev.exception.NullIdCourseException;
import by.mogilev.model.Course;

import java.util.List;

/**
 * Created by akiseleva on 03.03.2015.
 */

public interface CourseDAO {

    public void deleteCourse(Course course);
    public List<Course> getAllCourse();
    public Course getCourse(int id);
    public void registerCourse(Course course, String nameLector);
    public void updateCourse( Course course);
    public List getSelectedDao(String category);
    public List<Course> getCoursesForUser(int id);
    public Course getCourseByNameDAO(String courseName);


}
