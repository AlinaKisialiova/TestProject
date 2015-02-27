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
        users.put("a", new User("a", "1", "ROLE_LECTOR"));
        users.put("b", new User("b", "2", "ROLE_PARTICIPANT"));
    }

    public User getUser(String username) throws UsernameNotFoundException {
        if( !users.containsKey( username ) ){
            throw new UsernameNotFoundException( username + " not found" );
        }
        return users.get( username );
    }
}
