package by.mogilev.service;

/**
* Created by akiseleva on 27.02.2015.
*/
import by.mogilev.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class CustomUserDetailsService implements  UserDetailsService{


    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException{
       by.mogilev.model.User user= userDAO.getUser(username);

        List<GrantedAuthority> authorities = buildUserAuthority(user.getAuthority());
        return buildUserForAuthentication(user, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(by.mogilev.model.User user,
                                            List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(UserRole userRole) {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority(userRole.toString()));
        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

    public void setUserDao(UserDAOImp userDao) {
        this.userDAO = userDao;
    }

    public UserDAO getUserDao() {
        return userDAO;
    }
}