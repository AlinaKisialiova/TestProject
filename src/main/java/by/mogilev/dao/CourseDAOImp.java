package by.mogilev.dao;

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

/**
 * Created by akiseleva on 03.03.2015.
 */
@Repository
@Transactional
public class CourseDAOImp implements CourseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Transactional
    public void registerCourse(Course newCourse, String loginLector)  {
        Session session = this.sessionFactory.getCurrentSession();

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
       Course course=(Course) criteria.uniqueResult();
        Hibernate.initialize(course.getAttenders());
        Hibernate.initialize(course.getSubscribers());
        Hibernate.initialize(course.getEvalMap());

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
//        Session session = this.sessionFactory.getCurrentSession();
//       return sessionFactory.getCurrentSession().createQuery("from Course u where u.category=:category")
//                .setParameter("category",category).list();
        Session session = this.sessionFactory.getCurrentSession();

        Criteria criteria=session.createCriteria(Course.class);
        criteria.add(Restrictions.eq("category", category));
        List<Course> coursesList;
        coursesList=criteria.list();
        for (Course course : coursesList) {
            Hibernate.initialize(course.getAttenders());
            Hibernate.initialize(course.getSubscribers());
            Hibernate.initialize(course.getEvalMap());
        }

        return coursesList;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Course> getCoursesForUser(int id) {


        return null;
    }

    @Override
    public Course getCourseByNameDAO(String courseName) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria=session.createCriteria(Course.class);
        criteria.add(Restrictions.eq("nameCourse",courseName));
        return (Course) criteria.uniqueResult();
    }

    @Transactional
    public void deleteCourse(Course course) {
        Session session = this.sessionFactory.getCurrentSession();
        Hibernate.initialize(course.getSubscribers());
        if (course.getSubscribers().size() > 0) {

            for (User user : course.getSubscribers()) {
                Hibernate.initialize(user.getCoursesSubscribe());
                user.getCoursesSubscribe().remove(course);
            }
        }

        Hibernate.initialize(course.getAttenders());
        if(course.getAttenders().size()>0){

            for(User user : course.getAttenders()){
                Hibernate.initialize(user.getCoursesAttendee());
                user.getCoursesAttendee().remove(course);
            }
        }
        session.delete(course);
        session.flush();

    }

}
