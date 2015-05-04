package by.mogilev.service;

import by.mogilev.model.Course;
import by.mogilev.model.User;

import javax.mail.internet.AddressException;
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
    public Set<Course> getCoursesSubscribeOfUser(String username);

    /**
     * Method is return list of course on which user was attendeed
     * @param username
     * @return
     */
    public Set<Course> getCoursesAttendeeOfUser(String username);

    public  int getIdByUsername(String username);

    /**
     * Method add cheked course in list courses on which he is subscriber
     * @param username
     * @param id
     */
    public boolean addInSubscribers(String username, int id) throws AddressException;
    /**
     * Method add cheked course in list courses on which he is attenders
     * @param username
     * @param id_course
     */
    public void addInAttSet(String username, int id_course) throws Exception;

    public void removeFromAttSet(String username, int id_course) throws Exception;

    public User getUser(String userName) ;
}
