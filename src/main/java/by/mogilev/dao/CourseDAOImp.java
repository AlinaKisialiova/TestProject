package by.mogilev.dao;

import by.mogilev.dao.CourseDAO;
import by.mogilev.model.Course;
import by.mogilev.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void registerCourse(Course newCourse, String loginLector) {
        Session session = this.sessionFactory.getCurrentSession();
        if (newCourse == null) return;
        User lector;
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", loginLector));
        lector= (User) criteria.uniqueResult();
        newCourse.setLector(lector);
        session.save(newCourse);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Course> getAllCourse() {
        List<Course> coursesList = new ArrayList<Course>();
        Session session = this.sessionFactory.getCurrentSession();
        coursesList = session.createCriteria(Course.class).list();
        return coursesList;
    }

    @Transactional
    public Course getCourse(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Course.class);
        criteria.add(Restrictions.eq("id", id));
        return (Course) criteria.uniqueResult();
    }

 @Override
    public void updateCourse(Course course) {
        Session session = this.sessionFactory.getCurrentSession();
     session.update(course);
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Course> getCoursesForUser() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Course> getCoursesForLector() {
        return sessionFactory.getCurrentSession().createQuery("from Course").list();
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Course> getSelected(String category) {
        if(category.equals("All"))
            return getAllCourse();
        else
            return sessionFactory.getCurrentSession().createQuery("from Course u where u.category=:category")
                    .setParameter("category",category).list();
    }


    @Transactional
    public void deleteCourse(Course course) {
        if (course==null) return;
        Session session = this.sessionFactory.openSession();
        session.delete(course);
        session.flush();
    }

}
