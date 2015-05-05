package by.mogilev.service;

import by.mogilev.exception.NullIdCourseException;
import by.mogilev.exception.NullUserException;
import by.mogilev.model.Course;
import by.mogilev.model.User;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by akiseleva on 03.04.2015.
 */
public interface UserService {
    /**
     * Method is return list of course on which user was subscribed
     * @param username
     * @return
     */
    public Set<Course> getCoursesSubscribeOfUser(String username) throws NullUserException;

    /**
     * Method is return list of course on which user was attendeed
     * @param username
     * @return
     */
    public Set<Course> getCoursesAttendeeOfUser(String username) throws NullUserException;

    public  int getIdByUsername(String username) throws NullUserException;

    /**
     * Method add cheked course in list courses on which he is subscriber
     * @param username
     * @param id
     */
    public boolean addInSubscribers(String username, int id) throws AddressException, NullUserException, NullIdCourseException;
    /**
     * Method add cheked course in list courses on which he is attenders
     * @param username
     * @param id_course
     */
    public void addInAttSet(String username, int id_course) throws Exception;

    public void removeFromAttSet(String username, int id_course) throws Exception;

    public User getUser(String userName) throws NullUserException;

    /***
     * Method get username from Principal
     * @param request
     * @return
     */
    public String getUserFromSession(HttpServletRequest request) throws NullUserException;
}
