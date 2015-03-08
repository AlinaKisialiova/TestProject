package by.mogilev.service;

import by.mogilev.model.User;
import by.mogilev.model.UserRole;
import org.springframework.stereotype.Component;

/**
 * Created by Администратор on 08.03.2015.
 */
@Component
public class UserService implements UserActions{


        public User getUser(String userName) {
            return new User(userName, "1", UserRole.ROLE_LECTOR);
        }
    }

