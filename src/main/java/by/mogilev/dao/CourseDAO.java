package by.mogilev.dao;

import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.Course;

import java.util.List;

/**
 * Created by akiseleva on 03.03.2015.
 */

public interface CourseDAO {
    /**
     * Method delete checed course
     * @param course
     */
    public void deleteCourse(Course course);

    /**
     * Method return all course from db
     * @return
     */
    public List<Course> getAllCourse();

    /**
     * Get course for id
     * @param id
     * @return
     */
    public Course getCourse(int id);

    /**
     * Register new course
     * @param course
     * @param nameLector
     */
    public void registerCourse(Course course, String nameLector);

    /**
     * Update date about course
     * @param course
     */
    public void updateCourse( Course course);

    /**
     * Get list of course for checked category
     * @param category
     * @return
     */
    public List getSelectedDao(String category);

    /**
     * Get course for name of Course
     * @param courseName
     * @return
     */
    public Course getCourseByNameDao(String courseName);


    public List getCoursesSubscribersByUserDao(String userName) throws NotFoundUserException;

}
