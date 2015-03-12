package by.mogilev.service;

import by.mogilev.model.Course;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by akiseleva on 03.03.2015.
 */

public interface CourseDAO {

    public void deleteCourse(Course course) ;
    public List<Course> getAllCourse();
    public Course findCourse(int id);
    public void remidEv(int id, int grade);
    public void registerCourse(String category, String nameCourse, String description, String links, String duration ) ;
    public void updateCourse(int id, String category, String nameCourse, String description, String links, String duration ) ;

}
