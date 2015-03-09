package by.mogilev.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by akiseleva on 03.03.2015.
 */


public enum UserRole  {

    ROLE_LECTOR,
    ROLE_EMPLOYE,
    ROLE_MANAGER;

    UserRole() {
    }
}
