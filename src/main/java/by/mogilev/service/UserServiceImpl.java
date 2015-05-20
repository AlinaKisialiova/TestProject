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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Repository;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by akiseleva on 03.04.2015.
 */
@Repository
@Transactional
public class UserServiceImpl implements UserService {

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
    public List<Course> getCoursesSubscribersByUser(String username) throws NotFoundUserException {
        return courseDAO.getCoursesSubscribersByUserDao(username);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Course> getCoursesAttendeeByUser(String username) throws NotFoundUserException {
        if (username == null) throw new NotFoundUserException();
        User user = getUser(username);
        List<Course> coursesList = user.getCoursesAttendee();

        for (Course course : coursesList) {
            Hibernate.initialize(course.getAttenders());
            Hibernate.initialize(course.getSubscribers());
        }
        return coursesList;

    }


    @Override
    public boolean addInSubscribers(String username, int id_course) throws AddressException, NotFoundUserException, NotFoundCourseException {
        if (username == null) throw new NotFoundUserException();
        User userSubscr = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
        if (course == null)  throw new NotFoundCourseException();

        List<Course> courses = userSubscr.getCoursesSubscribe();
        if (courses.contains(course)) return false;

        courses.add(course);
        userDAO.updateUser(userSubscr);

        if (course.getSubscribers().size() >= Course.MIN_COUNT_SUBSCR) {
            course.setCourseStatus(CourseStatus.DELIVERED);
            InternetAddress[] emails = mailService.getRecipient(course.getSubscribers());
            mailService.sendEmail(id_course, Notification.COURSE_APPOINTED, emails, username,"");
        }

        return true;
    }

    @Override
    public boolean removeFromSubscribers(String username, int id_course) throws AddressException, NotFoundUserException, NotFoundCourseException {
        if (username == null) throw new NotFoundUserException();

        User userSubscr = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
        if (course == null) throw new NotFoundCourseException();

        List<Course> courses = userSubscr.getCoursesSubscribe();
        if (courses.contains(course)) {
            userSubscr.getCoursesSubscribe().remove(course);
            userDAO.updateUser(userSubscr);
            return true;
        }
        return false;
    }

    @Override
    public boolean addInAttSet(String username, int id_course) throws Exception {
        if (username == null) throw new NotFoundUserException();
        User userAtt = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
        if(course == null) throw new NotFoundCourseException();

        List<Course> courses = userAtt.getCoursesAttendee();
        if (!courses.contains(course) && course.getAttenders().size() < Course.MAX_COUNT_ATT) {
            courses.add(course);
            userDAO.updateUser(userAtt);
            return true;
        }

return false;

    }

    @Override
    public boolean removeFromAttSet(String username, int id_course) throws Exception {
        if (username == null) throw new NotFoundUserException();

        User userAtt = userDAO.getUser(username);
        Course course = courseDAO.getCourse(id_course);
        if(course == null) throw new NotFoundCourseException();
        List<Course> courses = userAtt.getCoursesAttendee();

        if (courses.contains(course)) {
            courses.remove(course);
            userDAO.updateUser(userAtt);

            if(course.getAttenders().size() == 0) {
                course.setCourseStatus(CourseStatus.APPROVE_KNOWLEDGE_MANAGER);
                courseDAO.updateCourse(course);
            }
            return true;
        }
        return false;

    }

    @Override
    public User getUser(String userName) throws NotFoundUserException {
        return userDAO.getUser(userName);
    }

    @Override
    public String getUserFromSession(HttpServletRequest request) throws NotFoundUserException {
        HttpSession session = request.getSession();
        SecurityContext ctx = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication auth = ctx.getAuthentication();
        if (auth.getName() == null) throw  new NotFoundUserException();
        return  auth.getName();
    }
}
