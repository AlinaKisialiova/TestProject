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

    /**
     * Method add cheked course in list courses on which he is subscriber
     * @param username
     * @param id
     */
    public String addInSubscribers(String username, int id);
    /**
     * Method add cheked course in list courses on which he is attenders
     * @param username
     * @param id_course
     */
    public String addInAttSet(String username, int id_course);
}
