package by.mogilev.service;

import by.mogilev.model.User;
import by.mogilev.model.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserDAOImp extends HibernateDaoSupport implements UserDAO {

public  UserDAOImp() {}

    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        HibernateTemplate result = super.createHibernateTemplate(sessionFactory);
        result.setAllowCreate(false);
        return result;
    }

    public User save(User objectToSave) {
        getSessionFactory().openSession().saveOrUpdate(objectToSave);
        return objectToSave;
    }

    public User getUser(String userName)  {
      Session session = getSessionFactory().openSession();
        return   (User) session.load(User.class, userName);
    }

    @Override
    public void addUser(User user)  {
        Session session=getSessionFactory().openSession();
            session.save(user);

    }

    public List<User> getAllUser()  {
        List<User> users = new ArrayList<User>();
           Session session = getSessionFactory().openSession();
            users = session.createCriteria(User.class).list();


        return users;
    }
}







