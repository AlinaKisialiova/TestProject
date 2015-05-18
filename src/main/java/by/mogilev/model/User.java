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
@Table(name = "USER")
public class User  {

    private int id;
    private String username;
    private String password;
    private UserRole authority;
    private String name;
    private String email;
    private Set<Course> course;
    private boolean enabled;
    private List<Course> coursesSubscribe = new ArrayList<Course>();
    private List<Course> coursesAttendee = new ArrayList<Course>();

    public User() {
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

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    public UserRole getAuthority() {
        return authority;
    }

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lector")
    public Set<Course> getCourse() {
        return course;
    }

    public void setCourse(Set<Course> course) {
        this.course = course;
    }

    @ManyToMany( fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "COURSE_SUBSCRIBERS",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_course"))
    public List<Course> getCoursesSubscribe() {
        return coursesSubscribe;
    }

    public void setCoursesSubscribe(List<Course> coursesSubscribe) {
        this.coursesSubscribe = coursesSubscribe;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "COURSE_ATTENDERS",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_course"))
    public List<Course> getCoursesAttendee() {
        return coursesAttendee;
    }

    public void setCoursesAttendee(List<Course> coursesAttendee) {
        this.coursesAttendee = coursesAttendee;
    }

//    @Override
//    public int hashCode() {
//        int result = id;
//        result = 31 * result + username.hashCode();
//        result = 31 * result + password.hashCode();
//        result = 31 * result + authority.hashCode();
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        return result;
//    }



    @Override
    public boolean equals(Object obj) {
//        if (obj == null || !(obj instanceof User))
//            return false;
      User objUser = (User) obj;
        return this.getId() == objUser.getId();
    }

}
