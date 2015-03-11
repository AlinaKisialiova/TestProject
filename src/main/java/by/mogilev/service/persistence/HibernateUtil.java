//package by.mogilev.service.persistence;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//
///**
//* Created by akiseleva on 11.03.2015.
//*/
//public class HibernateUtil {
//
//    private static SessionFactory sessionFactory;
//
//    static {
//        try {
//
//            Session session = sessionFactory.getCurrentSession();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//}