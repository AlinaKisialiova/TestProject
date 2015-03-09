package by.mogilev.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

/**
* Created by akiseleva on 27.02.2015.
*/
@PersistenceContext(unitName = "trainingCenter", type = PersistenceContextType.EXTENDED)

@Entity
@Table(name ="USERS")
public class User implements UserDetails {

    private int id;
    private String username;
    private String password;
    Collection<GrantedAuthority> authorities;

    public User(String username, String password, UserRole roles) {
        super();
        this.username = username;
        this.password = password;
        this.setRoles(roles);
    }
    public User(){}

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
    @Column(name ="authorities")
    @Enumerated(EnumType.STRING)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "password")
            public String getPassword() {
        return password;
    }
    @Column(name ="username")
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
