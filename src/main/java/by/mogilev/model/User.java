package by.mogilev.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
* Created by akiseleva on 27.02.2015.
*/
public class User implements UserDetails {
    private static final long serialVersionUID = 8266525488057072269L;
    private String username;
    private String password;
    Collection<GrantedAuthority> authorities;

    public User(String username, String password, UserRole roles) {
        super();
        this.username = username;
        this.password = password;
        this.setRoles(roles);
    }

    public void setRoles(UserRole roles) {
        this.authorities = new HashSet<GrantedAuthority>();


                GrantedAuthority grandAuthority =  new SimpleGrantedAuthority(roles.name());

                this.authorities.add(grandAuthority);

        }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
