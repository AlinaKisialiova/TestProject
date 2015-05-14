package by.mogilev.service;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
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
    public Set<Course> getCoursesSubscribeOfUser(String username) throws NotFoundUserException;

    /**
     * Method is return list of course on which user was attendeed
     * @param username
     * @return
     */
    public Set<Course> getCoursesAttendeeOfUser(String username) throws NotFoundUserException;

    public  int getIdByUsername(String username) throws NotFoundUserException;

    /**
     * Method add cheked course in list courses on which he is subscriber
     * @param username
     * @param id
     */
    public boolean addInSubscribers(String username, int id) throws AddressException, NotFoundUserException, NotFoundCourseException;
    /**
     * Method remove  cheked course from list courses on which he is subscriber
     * @param username
     * @param id
     */
    public boolean removeFromSubscribers(String username, int id) throws AddressException, NotFoundUserException, NotFoundCourseException;
    /**
     * Method add cheked course in list courses on which he is attenders
     * @param username
     * @param id_course
     */
    public boolean addInAttSet(String username, int id_course) throws Exception;

    public boolean removeFromAttSet(String username, int id_course) throws Exception;

    public User getUser(String userName) throws NotFoundUserException;

    /***
     * Method get username from Principal
     * @param request
     * @return
     */
    public String getUserFromSession(HttpServletRequest request) throws NotFoundUserException;
}
