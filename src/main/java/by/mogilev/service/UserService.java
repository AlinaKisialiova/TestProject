package by.mogilev.service;

import by.mogilev.model.User;
import by.mogilev.model.UserRole;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 08.03.2015.
 */
@Component
public class UserService implements UserActions{

        public User getUser(String userName) {
            return new User(userName, "1", UserRole.ROLE_LECTOR);
        }

//    @Autowired
//    private SessionFactory sessionFactory;
//
//    private Session openSession() {
//        return sessionFactory.getCurrentSession();
//    }
//
//    public User getUser(String login) {
//        List<User> userList = new ArrayList<User>();
//        Query query = openSession().createQuery("from USER u where u.login = :login");
//        query.setParameter("login", login);
//        userList = query.list();
//        if (userList.size() > 0)
//            return userList.get(0);
//        else
//            return null;
//    }

    }

