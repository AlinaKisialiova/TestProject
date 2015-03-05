package by.mogilev.service;

import by.mogilev.model.Course;
import by.mogilev.model.Employe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akiseleva on 03.03.2015.
 */
@Service
public class CourseService implements CourseActions {

    static List<Course> courseList = new ArrayList<Course>();
    {
       courseList.add(new Course(1,"Project Management", "Project Management", "Aleksander Ivanov", "24 hour", " ", " ",15, 10, 5, true, new ArrayList<Employe>()));
       courseList.add(new Course(2,"NET Technology", "Development", "elvis", "36 hour", " ", " ",25, 15, 5, true, new ArrayList<Employe>()));
       courseList.add(new Course(3,"COM/DCOM Technology", "Development", " Mihail Petrov", "14 hour", " ", " ",7, 5, 5, false, new ArrayList<Employe>()));
    }

    @Override
    public List<Course> getAllCourse() {
        return courseList;
    }

    @Override
    public Course findCourse(int id) {
        for(Course course : courseList)        {
            if (id==course.getId())
                return course;
        }
        return null;
    }

    @Override
    public void registerCourse(String category, String nameCourse, String description, String links, String duration) {

    }

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public void deleteCourse(Course course) {

    }


}
