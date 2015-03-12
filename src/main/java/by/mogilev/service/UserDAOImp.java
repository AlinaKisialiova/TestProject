package by.mogilev.service;

import by.mogilev.model.User;
import by.mogilev.model.UserRole;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Transactional
public class UserDAOImp  implements UserDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    public  UserDAOImp() {}


    public void save(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(user);

    }

    public User getUser(String userName)  {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria=session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username",userName));
        return (User) criteria.uniqueResult();
    }

    @Override
    public void addUser(User user)  {
        Session session = this.sessionFactory.getCurrentSession();;
            session.save(user);

    }
    @SuppressWarnings("unchecked")
    public List<User> getAllUser()  {
        List<User> users = new ArrayList<User>();
        Session session = this.sessionFactory.getCurrentSession();
          users=session.createQuery("from USER").list();
        return users;
    }
}







