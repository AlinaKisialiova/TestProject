package by.mogilev.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
* Created by akiseleva on 27.02.2015.
*/
@PersistenceContext(unitName = "trainingCenter", type = PersistenceContextType.EXTENDED)

@Entity
@Table(name ="USER")
public class User implements UserDetails {

    private int id;
    private String username;
    private String password;
    UserRole authority;
    private String name;
    private String email;
    private List<Course> course;


    public User(){}

    public User(String username, String password, UserRole role) {
        super();
        this.username = username;
        this.password = password;
        this.authority=role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority.getAuthority();
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }
    @Column(name ="username")
    public String getUsername() {
        return username;
    }

    @Column(name ="authority")
    @Enumerated(EnumType.STRING)
    public UserRole getAuthority() {
        return authority;    }

    public void setAuthority(UserRole authority) {
        this.authority = authority;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Course")
    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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
