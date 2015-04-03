package by.mogilev.service;

import by.mogilev.model.Course;

import java.util.Set;

/**
 * Created by akiseleva on 03.04.2015.
 */
public interface UserService {
    /**
     * Method is return list of course on which user was subscribed
     * @param id
     * @return
     */
    public Set<Course> getCoursesSubscribeOfUser(int id);

    /**
     * Method is return list of course on which user was attendeed
     * @param id
     * @return
     */
    public Set<Course> getCoursesAttendeeOfUser(int id);

    public  int getIdByUsername(String username);

//    public void addInAttendee();
}
