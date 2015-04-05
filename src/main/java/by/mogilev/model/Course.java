package by.mogilev.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by akiseleva on 26.02.2015.
 */

@Entity
@Table(name = "COURSE")
public class Course implements Serializable {
    private int id;
    private String category;
    private String nameCourse;
    private User lector;
    private String duration;
    private String description;
    private String links;
    private int evaluation;
    private boolean delivered;
    private Set<User> attenders = new HashSet<User>();
    private Set<User> subscribers = new HashSet<User>();


    public Course(String category, String nameCourse, String description, String links, String duration, User lector) {
        this.category = category;
        this.nameCourse = nameCourse;
        this.description = description;
        this.links = links;
        this.duration = duration;
        this.lector = lector;

    }

    public Course() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_course")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "nameCourse")
    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    @Column(name = "duration")
    public String getDuration() {
        return duration;

    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "links")
    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    @Column(name = "evaluation")
    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    public User getLector() {
        return lector;
    }

    public void setLector(User lector) {
        this.lector = lector;
    }

    @ManyToMany( mappedBy = "coursesAttendee",
            cascade = CascadeType.ALL)
    public Set<User> getAttenders() {
        return attenders;
    }

    public void setAttenders(Set<User> attendees) {
        this.attenders = attendees;
    }

    @ManyToMany( mappedBy = "coursesSubscribe",
            cascade = CascadeType.ALL)
    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }
}
