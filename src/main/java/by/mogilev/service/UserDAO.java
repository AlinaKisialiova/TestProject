package by.mogilev.service;

import by.mogilev.model.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Администратор on 08.03.2015.
 */
public interface UserDAO {
    public User getUser(String userName) ;
    public void addUser(User user) ;
    public List<User> getAllUser() ;
}
