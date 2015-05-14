package by.mogilev.service;

import by.mogilev.dao.CourseDAO;
import by.mogilev.dao.UserDAO;
import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.Course;
import by.mogilev.model.CourseStatus;
import by.mogilev.model.Notification;
import by.mogilev.model.User;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Set;

/**
 * Created by akiseleva on 03.04.2015.
 */
@Repository
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CourseDAO courseDAO;


    @Autowired
    private MailService mailService;

    public UserServiceImpl() {
    }


    @SuppressWarnings("unchecked")
    @Override
    public Set<Course> getCoursesSubscribeOfUser(String username) throws NotFoundUserException {

        if (username == null) throw new NotFoundUserException();
         User user = getUser(username);
        Set<Course> coursesList = user.getCoursesSubscribe();
        for (Course course : coursesList) {
            Hibernate.initialize(course.getAttenders());
            Hibernate.initialize(course.getSubscribers());
        }
        return coursesList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Course> getCoursesAttendeeOfUser(String username) throws NotFoundUserException {
        if (username == null) throw new NotFoundUserException();
        User user = getUser(username);
        Set<Course> coursesList = user.getCoursesAttendee();

        for (Course course : coursesList) {
            Hibernate.initialize(course.getAttenders());
            Hibernate.initialize(course.getSubscribers());
        }
        return coursesList;

    }

    @Override
    public int getIdByUsername(String username) throws NotFoundUserException {
        if (username == null) throw new NotFoundUserException();
        return userDAO.getIdByUsername(username);
    }

    @Override
    public boolean addInSubscribers(String username, int id_course) throws AddressException, NotFoundUserException, NotFoundCourseException {
        if (username == null) throw new NotFoundUserException();
        if (id_course < 1) throw new NotFoundCourseException();

        User userSubscr = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
        Set<Course> courses = userSubscr.getCoursesSubscribe();
        if (courses.contains(course)) return false;

        courses.add(course);
        userDAO.updateUser(userSubscr);

        if (course.getSubscribers().size() >= Course.MIN_COUNT_SUBSCR) {
            course.setCourseStatus(CourseStatus.DELIVERED);
            InternetAddress[] emails = mailService.getRecipientSubsc(course);
            mailService.sendEmail(id_course, Notification.COURSE_APPOINTED, emails, username);
        }

        return true;
    }

    @Override
    public boolean removeFromSubscribers(String username, int id_course) throws AddressException, NotFoundUserException, NotFoundCourseException {
        if (username == null) throw new NotFoundUserException();
        if (id_course < 1) throw new NotFoundCourseException();

        User userSubscr = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
        Set<Course> courses = userSubscr.getCoursesSubscribe();
        if (courses.contains(course)) {
            userSubscr.getCoursesSubscribe().remove(course);
            userDAO.updateUser(userSubscr);
            return true;
        }
        return false;
    }

    @Override
    public void addInAttSet(String username, int id_course) throws Exception {
        if (username == null) throw new NotFoundUserException();
        if (id_course < 1) throw new NotFoundCourseException();

        User userAtt = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
        Set<Course> courses = userAtt.getCoursesAttendee();
        if (!courses.contains(course) && course.getAttenders().size() < Course.MAX_COUNT_ATT) {
            courses.add(course);
            userDAO.updateUser(userAtt);
        } else throw new Exception();


    }

    @Override
    public void removeFromAttSet(String username, int id_course) throws Exception {
        if (username == null) throw new NotFoundUserException();
        if (id_course < 1) throw new NotFoundCourseException();

        User userAtt = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
        Set<Course> courses = userAtt.getCoursesAttendee();

        if (courses.contains(course)) {
            courses.remove(course);
            userDAO.updateUser(userAtt);
        }

    }

    @Override
    public User getUser(String userName) throws NotFoundUserException {
        if (userName == null) throw new NotFoundUserException();

        return userDAO.getUser(userName);
    }

    @Override
    public String getUserFromSession(HttpServletRequest request) throws NotFoundUserException {
        String userName = "";
        Principal principal = request.getUserPrincipal();
        if (principal != null && principal.getName() != null) {
            userName = principal.getName();
        } else throw new NotFoundUserException();

        return userName;
    }
}
