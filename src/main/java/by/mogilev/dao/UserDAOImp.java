package by.mogilev.dao;

import by.mogilev.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class UserDAOImp  implements UserDAO {

@Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public User getUser(String username)  {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria=session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username",username));
        return (User) criteria.uniqueResult();
    }

    @Override
    public void updateUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
    }


}







