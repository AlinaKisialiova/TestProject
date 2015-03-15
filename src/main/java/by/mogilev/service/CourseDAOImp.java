package by.mogilev.service;

import by.mogilev.model.Course;
import by.mogilev.model.User;
import javassist.NotFoundException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.transaction.Transactional;
import java.sql.SQLException;
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

    public CourseDAOImp() {
    }


    @Transactional
    public void registerCourse(String category, String nameCourse, String description, String links, String duration, String loginLector) {
        Session session = this.sessionFactory.getCurrentSession();
        User lector;
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", loginLector));
        lector= (User) criteria.uniqueResult();
        session.save(new Course(category, nameCourse, description, links, duration, lector.getName()));
    }

    @Transactional
    public List<Course> getAllCourse() {
        List<Course> coursesList = new ArrayList<Course>();
        Session session = this.sessionFactory.getCurrentSession();
        coursesList = session.createCriteria(Course.class).list();
        return coursesList;
    }

    @Transactional
    public Course findCourse(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Course.class);
        criteria.add(Restrictions.eq("id", id));
        return (Course) criteria.uniqueResult();

    }


    @Override
    public void updateCourse(int id, String category, String nameCourse, String description, String links, String duration) {
        Session session = this.sessionFactory.getCurrentSession();
        Course changeCourse = findCourse(id);
        changeCourse.setCategory(category);
        changeCourse.setNameCourse(nameCourse);
        changeCourse.setDescription(description);
        changeCourse.setLinks(links);
        changeCourse.setDuration(duration);
        session.saveOrUpdate(changeCourse);
    }

    @Override
    public void remidEv(int id, int grade) {
        Session session = this.sessionFactory.getCurrentSession();
        Course changeEvalCourse = findCourse(id);
        changeEvalCourse.setEvaluation(grade);
        session.saveOrUpdate(changeEvalCourse);

    }

    @Transactional
    public void deleteCourse(Course course) {
        if (course==null) return;
        Session session = this.sessionFactory.openSession();
        session.delete(course);
        session.flush();
    }

}
