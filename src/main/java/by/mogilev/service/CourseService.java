package by.mogilev.service;

import by.mogilev.model.Course;
import by.mogilev.model.Participant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akiseleva on 03.03.2015.
 */
@Service
public class CourseService implements CourseActions {

    private List<Course> courseList = new ArrayList<Course>();
    {
       courseList.add(new Course("Project Management", "Project Management", "Aleksander Ivanov", "24 hour", " ", " ",15, 10, 5, true, new ArrayList<Participant>()));
       courseList.add(new Course("NET Technology", "Development", "elvis", "36 hour", " ", " ",25, 15, 5, true, new ArrayList<Participant>()));
       courseList.add(new Course("COM/DCOM Technology", "Development", " Mihail Petrov", "14 hour", " ", " ",7, 5, 5, false, new ArrayList<Participant>()));
    }

    @Override
    public List<Course> getAllCourse() {
        return courseList;
    }

    @Override
    public void addCourse(Course course) {

    }

    @Override
    public void deleteCourse(Course course) {

    }


}
