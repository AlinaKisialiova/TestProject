package by.mogilev.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
* Created by akiseleva on 27.02.2015.
*/

@Entity
@Table(name ="USER")
public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    UserRole authority;
    private String name;
    private String email;
    private Set<Course> course;
    private boolean enabled;
    private Set<Course> coursesSubscribe = new HashSet<Course>();
    private Set<Course> coursesAttendee = new HashSet<Course>();


    public User(){}

//    public User(String username, String password, UserRole role) {
//        this.username = username;
//        this.password = password;
//        this.authority=role;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy="lector" )
    public Set<Course> getCourse() {
        return course;
    }
    public void setCourse(Set<Course> course) {
        this.course = course;
    }

    @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name="COURSE_SUBSCRIBERS",
            joinColumns=@JoinColumn(name="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_course"))
    public Set<Course> getCoursesSubscribe() {
        return coursesSubscribe;
    }

    public void setCoursesSubscribe(Set<Course> coursesSubscribe) {
        this.coursesSubscribe = coursesSubscribe;
    }
    @ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name="COURSE_ATTENDERS",
            joinColumns=@JoinColumn(name="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_course"))
    public Set<Course> getCoursesAttendee() {
        return coursesAttendee;
    }

    public void setCoursesAttendee(Set<Course> coursesAttendee) {
        this.coursesAttendee = coursesAttendee;
    }

}
