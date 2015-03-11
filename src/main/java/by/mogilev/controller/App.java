package by.mogilev.controller;

import by.mogilev.model.User;
import by.mogilev.model.UserRole;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
* Created by akiseleva on 11.03.2015.
*/
public class App {
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static void main( String[] args )
{
    App sessionFactory=new App();

    System.out.println("Maven + Hibernate");
    Session session=sessionFactory.getSessionFactory().getCurrentSession();

    session.beginTransaction();
    User user = new User("user", "1", UserRole.ROLE_LECTOR);

    session.save(user);
    session.getTransaction().commit();
}
}
