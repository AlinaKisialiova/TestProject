package by.mogilev.dao;

import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.Course;
import by.mogilev.model.CourseStatus;
import by.mogilev.model.User;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by akiseleva on 03.03.2015.
 */
@Repository
@Transactional
public class CourseDAOImp implements CourseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDAO userDAO;

    @SuppressWarnings("unchecked")
    @Transactional
    public void registerCourse(Course newCourse, String loginLector) {
        Session session = this.sessionFactory.getCurrentSession();
        User lector;
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", loginLector));
        lector = (User) criteria.uniqueResult();
        newCourse.setLector(lector);
        newCourse.setCourseStatus(CourseStatus.NOT_APPROVE);
        session.save(newCourse);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Course> getAllCourse() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Course> coursesList = session.createCriteria(Course.class).list();
        for (Course course : coursesList) {
            Hibernate.initialize(course.getAttenders());
            Hibernate.initialize(course.getSubscribers());
            Hibernate.initialize(course.getEvalMap());
        }
        return coursesList;
    }

    @Transactional
    public Course getCourse(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Course.class);
        criteria.add(Restrictions.eq("id", id));
        Course course = (Course) criteria.uniqueResult();
        if (course != null) {
            Hibernate.initialize(course.getAttenders());
            Hibernate.initialize(course.getSubscribers());
            Hibernate.initialize(course.getEvalMap());
        }
        return course;
    }

    @Override
    public void updateCourse(Course course) {
        Session session = this.sessionFactory.getCurrentSession();
        Hibernate.initialize(course.getAttenders());
        Hibernate.initialize(course.getSubscribers());
        Hibernate.initialize(course.getEvalMap());
        session.update(course);
    }

    @Override
    public List getSelectedDao(String category) {
        Session session = this.sessionFactory.getCurrentSession();

        Criteria criteria = session.createCriteria(Course.class);
        criteria.add(Restrictions.eq("category", category));
        List<Course> coursesList;
        coursesList = criteria.list();
        for (Course course : coursesList) {
            Hibernate.initialize(course.getAttenders());
            Hibernate.initialize(course.getSubscribers());
            Hibernate.initialize(course.getEvalMap());
        }

        return coursesList;
    }


    @Override
    public Course getCourseByNameDao(String courseName) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Course.class);
        criteria.add(Restrictions.eq("nameCourse", courseName));
        return (Course) criteria.uniqueResult();
    }

    @Override
    public List<Course> getCoursesSubscribeByUserDao(String userName) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", userName));
        User user = (User) criteria.uniqueResult();
        List<Course> courses = user.getCoursesAttendee();
        Hibernate.initialize(courses);
        return courses;
    }

    @Override
    public List<Course> getCoursesSubscribersByUserDao(String userName) throws NotFoundUserException {
        Session session = this.sessionFactory.getCurrentSession();

        List coursesId;
        Criteria criteria = session.createCriteria(User.class, "u");
        criteria.setFetchMode("coursesSubscribe", FetchMode.JOIN);
        criteria.createAlias("coursesSubscribe", "sc")
                .setProjection(Projections.property("sc.id"));
        coursesId = criteria.list();

        List<Course> courses = new ArrayList<Course>();
        for (Object o : coursesId)
            courses.add(getCourse((Integer) o));


        return courses;

    }


    public void deleteCourse(Course course) {
        Session session = this.sessionFactory.getCurrentSession();
        Hibernate.initialize(course.getSubscribers());


        if (course.getSubscribers().size() > 0) {
            for (User user : course.getSubscribers()) {
                user.getCoursesSubscribe().remove(course);
                session.update(user);
            }
        }

        Hibernate.initialize(course.getAttenders());
        if (course.getAttenders().size() > 0) {
            for (User user : course.getAttenders()) {
                user.getCoursesAttendee().remove(course);
                session.update(user);
            }

        }
        session.update(course);
        session.delete(course);


    }

}
