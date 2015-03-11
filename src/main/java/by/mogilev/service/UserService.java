package by.mogilev.service;

import by.mogilev.model.User;
import by.mogilev.model.UserRole;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserService implements UserActions{
    @Autowired
    private SessionFactory sessionFactory;


    @SuppressWarnings("unchecked")
   public User getUser(String userName) {
           List<User> users = new ArrayList<User>();
           users = sessionFactory.getCurrentSession()
                   .createQuery("from USER where username=?")
                   .setParameter(0, userName).list();

           if (users.size() > 0) {
               return users.get(0);
           } else {
               return null;
           }

       }

   }




