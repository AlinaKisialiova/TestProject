package by.mogilev.dao;

import by.mogilev.exception.NullUserException;
import by.mogilev.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class UserDAOImp  implements UserDAO {

@Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void save(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);

    }

    public User getUser(String username)  {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria=session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username",username));
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
        Criteria criteria=session.createCriteria(User.class);
        return  criteria.list();

//          users=session.createQuery("from USER").list();
//        return users;
    }

    @Override
    public void updateUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public int getIdByUsername(String username) throws NullUserException {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        User user = (User) criteria.uniqueResult();
        return user.getId();
    }
}







