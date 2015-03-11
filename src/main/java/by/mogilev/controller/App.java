package by.mogilev.controller;

import by.mogilev.model.User;
import by.mogilev.model.UserRole;

import by.mogilev.service.Factory;
import by.mogilev.service.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;
import java.util.List;

/**
* Created by akiseleva on 11.03.2015.
*/
public class App {

    public static void main( String[] args ) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"hibernateConfig.xml"}, true);
        UserDAO userDao = (UserDAO) context.getBean("dataDaoUser");
        User data1 = new User("Alex", "1",UserRole.ROLE_LECTOR);
        userDao.addUser(data1);



    }
}
