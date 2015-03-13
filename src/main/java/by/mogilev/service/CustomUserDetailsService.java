package by.mogilev.service;

/**
 * Created by akiseleva on 27.02.2015.
 */

import by.mogilev.model.UserRole;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {

        by.mogilev.model.User user = userDAO.getUser(username);

        List<GrantedAuthority> authorities = buildUserAuthority(user.getAuthority());
        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(by.mogilev.model.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getUsername(),
                user.getPassword(), true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(UserRole userRole) {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority(userRole.toString()));
        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}