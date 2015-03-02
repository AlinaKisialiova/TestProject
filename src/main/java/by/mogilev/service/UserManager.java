package by.mogilev.service;

import by.mogilev.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by akiseleva on 27.02.2015.
 */
@Service
public class UserManager {
    private HashMap<String, User> users;

    public UserManager() {
        users = new HashMap<String, User>();
        users.put("elvis", new User("elvis", "1", "ROLE_LECTOR"));
        users.put("bob", new User("bob", "2", "ROLE_PARTICIPANT"));
        users.put("charli", new User("charli", "3", "ROLE_MANAGER"));
    }

    public User getUser(String username) throws UsernameNotFoundException {
        if (!users.containsKey(username)) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return users.get(username);
    }
}
