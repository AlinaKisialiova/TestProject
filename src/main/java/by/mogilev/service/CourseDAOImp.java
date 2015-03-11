package by.mogilev.service;

import by.mogilev.model.Course;
import by.mogilev.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akiseleva on 03.03.2015.
 */
@Component
public class CourseDAOImp extends HibernateDaoSupport implements CourseDAO {

    public CourseDAOImp(){};

    public void registerCourse(String category, String nameCourse, String description, String links, String duration)  {

          Session  session = getSessionFactory().openSession();
            session.save(new Course(category, nameCourse, description, links, duration));
            session.getTransaction().commit();
        //courseList.add(new Course(category, nameCourse, description, links, duration));
    }

    public List<Course> getAllCourse()  {
        List<Course> coursesList = new ArrayList<Course>();
        Session session = getSessionFactory().openSession();
        coursesList = session.createCriteria(Course.class).list();
        return  coursesList;
    }


    public Course findCourse(int id) {
         Session   session = getSessionFactory().openSession();
          Course  course = (Course) session.load(Course.class, id);
        return course;

    }

    public void addCourse(Course course)  {

       Session session = getSessionFactory().openSession();
        session.save(course);
        session.getTransaction().commit();

    }


    public void deleteCourse(Course course)  {

           Session session = getSessionFactory().openSession();
            session.delete(course);
            session.getTransaction().commit();

    }

    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate result = super.createHibernateTemplate(sessionFactory);
        result.setAllowCreate(false);
        return result;
    }
}
//    static List<Course> courseList = new ArrayList<Course>();
//
//    {
//        if (courseList.isEmpty()) {
//            courseList.add(new Course(1, "Project Management", "Project Management", "Aleksander Ivanov", "24 hour", " ", " ", 15, 10, 5, true, new ArrayList<User>()));
//            courseList.add(new Course(2, "NET Technology", "Development", "elvis", "36 hour", " ", " ", 25, 15, 5, true, new ArrayList<User>()));
//            courseList.add(new Course(3, "COM/DCOM Technology", "Development", " Mihail Petrov", "14 hour", " ", " ", 7, 5, 5, false, new ArrayList<User>()));
//        }
//    }