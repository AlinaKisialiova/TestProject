package by.mogilev.service;

import by.mogilev.model.Course;

import java.util.List;

/**
 * Created by akiseleva on 03.03.2015.
 */

public interface CourseActions {
    public void addCourse(Course course);
    public void deleteCourse(Course course);
    public List<Course> getAllCourse();
    public Course findCourse(int id);
    public void registerCourse(String category, String nameCourse, String description, String links, String duration );

}
