package by.mogilev.service;

import by.mogilev.dao.UserDAO;
import by.mogilev.model.Course;
import by.mogilev.model.User;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
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

    public  UserServiceImpl(){}



    @SuppressWarnings("unchecked")
    @Override
    public Set<Course> getCoursesSubscribeOfUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        if(id==0) throw new NullPointerException("Id user for get list of subscribers not find.");

        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("id", id));
        User user=(User)criteria.uniqueResult();
        Set<Course> coursesList;
        coursesList=user.getCoursesSubscribe();

        for (Course course : coursesList) {
            Hibernate.initialize(course.getAttenders());
            Hibernate.initialize(course.getSubscribers());
        }
        return coursesList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Course> getCoursesAttendeeOfUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        if(id==0) return null;

        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("id", id));
        User user=(User)criteria.uniqueResult();
        Set<Course> coursesList;
        coursesList=user.getCoursesAttendee();

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
        User user=(User)criteria.uniqueResult();
        return user.getId();
    }
}
