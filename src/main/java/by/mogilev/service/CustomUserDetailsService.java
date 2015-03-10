package by.mogilev.service;

/**
* Created by akiseleva on 27.02.2015.
*/
import by.mogilev.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserService userManager;

    private User user;

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userManager.getUser(username);
    }

}