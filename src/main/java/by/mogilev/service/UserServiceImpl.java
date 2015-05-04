package by.mogilev.service;

import by.mogilev.dao.CourseDAO;
import by.mogilev.dao.UserDAO;
import by.mogilev.exception.NullIdCourseException;
import by.mogilev.exception.NullUserException;
import by.mogilev.model.Course;
import by.mogilev.model.CourseStatus;
import by.mogilev.model.Notification;
import by.mogilev.model.User;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.transaction.Transactional;
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
    public Set<Course> getCoursesSubscribeOfUser(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        if (username == null) throw new NullPointerException("Username for get list of subscribers is empty.");

        int id_user=getIdByUsername(username);
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("id", id_user));
        User user = (User) criteria.uniqueResult();
        Set<Course> coursesList;
        coursesList = user.getCoursesSubscribe();

        for (Course course : coursesList) {
            Hibernate.initialize(course.getAttenders());
            Hibernate.initialize(course.getSubscribers());
        }
        return coursesList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Course> getCoursesAttendeeOfUser(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        if (username == null) throw new NullPointerException("Username for get list of attenders is empty.");

        int id_user=getIdByUsername(username);

        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("id", id_user));
        User user = (User) criteria.uniqueResult();
        Set<Course> coursesList;
        coursesList = user.getCoursesAttendee();

        for (Course course : coursesList) {
            Hibernate.initialize(course.getAttenders());
            Hibernate.initialize(course.getSubscribers());
        }
        return coursesList;

    }

    @Override
    public int getIdByUsername(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        User user = (User) criteria.uniqueResult();
        return user.getId();
    }

    @Override
    public boolean addInSubscribers(String username, int id_course) throws AddressException {
        if (username == null || id_course < 1) throw new NullPointerException("Username or id_course is null in addInSet()");

        User userSubscr = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
        Set<Course> courses = userSubscr.getCoursesSubscribe();
        if (courses.contains(course)) {
            userSubscr.getCoursesSubscribe().remove(course);
            userDAO.updateUser(userSubscr);
            return false;
        }

        courses.add(course);
        userDAO.updateUser(userSubscr);


        if (course.getSubscribers().size() >= Course.MIN_COUNT_SUBSCR) {
            course.setCourseStatus(CourseStatus.DELIVERED);
            InternetAddress[] emails = mailService.getRecipient(course);
            mailService.sendEmail(id_course, Notification.COURSE_APPOINTED, emails, username);
        }
        return true;
    }

    @Override
    public void addInAttSet(String username, int id_course) throws Exception {
        if (username == null) throw new NullUserException();
        if (id_course < 1) throw  new NullIdCourseException();

        User userAtt = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
            Set<Course> courses = userAtt.getCoursesAttendee();
        if(! courses.contains(course) && course.getAttenders().size() < Course.MAX_COUNT_ATT) {
            courses.add(course);
            userDAO.updateUser(userAtt);
        }
        else throw new Exception();



    }

    @Override
    public void removeFromAttSet(String username, int id_course) throws Exception {
        if (username == null || id_course < 1) throw new NullPointerException("Username or id_course is null in addInSet()");
        User userAtt = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
        Set<Course> courses = userAtt.getCoursesAttendee();

        if (courses.contains(course)) {
            courses.remove(course);
            userDAO.updateUser(userAtt);
        }

        else throw new Exception();
    }

    @Override
    public User getUser(String userName) {
        if (userName == null) throw new NullPointerException("Username is null in getUser()");

     return userDAO.getUser(userName);
    }
}
