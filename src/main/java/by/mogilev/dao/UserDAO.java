package by.mogilev.dao;

import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.User;

import java.util.List;

/**
 * Created by Администратор on 08.03.2015.
 */
public interface UserDAO {
    public User getUser(String userName) ;
    public void addUser(User user) ;
    public List<User> getAllUser() ;
    public void updateUser(User user);
    public int getIdByUsername(String username) throws NotFoundUserException;
}
