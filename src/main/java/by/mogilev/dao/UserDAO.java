package by.mogilev.dao;

import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.User;

/**
 * Created by Администратор on 08.03.2015.
 */
public interface UserDAO {
    /**
     * Get user for username
     * @param userName
     * @return
     */
    public User getUser(String userName);

    /**
     * update date for user
     * @param user
     */
    public void updateUser(User user);

    /**
     * Get id course for his name
     * @param username
     * @return
     * @throws NotFoundUserException
     */
    public int getIdByUsername(String username) throws NotFoundUserException;
}
