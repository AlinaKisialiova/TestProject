package by.mogilev.model;

import com.sun.javafx.scene.layout.region.Margins;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by akiseleva on 26.02.2015.
 */

@Entity
@Table(name = "COURSE")
public class Course implements Serializable {

    public final static int MIN_COUNT_SUBSCR = 2;
    public final static int MAX_COUNT_ATT = 15;

    private int id;
    private String category;
    private String nameCourse;
    private User lector;
    private String duration;
    private String description;
    private String links;
    private int evaluation;
    private Set<Course> course;
    private CourseStatus courseStatus;
    private String departmentManagerReason;
    private String  knowledgeManagerReason;

    @Column(name = "departmentManagerReason")
    public String getDepartmentManagerReason() {
        return departmentManagerReason;
    }

    public void setDepartmentManagerReason(String departmentManagerReason) {
        this.departmentManagerReason = departmentManagerReason;
    }

    @Column(name = "knowledgeManagerReason")
    public String getKnowledgeManagerReason() {
        return knowledgeManagerReason;
    }

    public void setKnowledgeManagerReason(String knowledgeManagerReason) {
        this.knowledgeManagerReason = knowledgeManagerReason;
    }

    private Set<User> attenders = new HashSet<User>();
    private Set<User> subscribers = new HashSet<User>();
    private Map<User, Integer> evalMap = new HashMap<User, Integer>();


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
    @Column(name = "status", columnDefinition = "NOT_APPROVE")
    @Enumerated(EnumType.STRING)
    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
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


    @ManyToOne
    @JoinColumn(name = "id_user")
    public User getLector() {
        return lector;
    }

    public void setLector(User lector) {
        this.lector = lector;
    }

    @ManyToMany(mappedBy = "coursesAttendee",
            cascade = CascadeType.ALL)
    public Set<User> getAttenders() {
        return attenders;
    }

    public void setAttenders(Set<User> attendees) {
        this.attenders = attendees;
    }

    @ManyToMany(mappedBy = "coursesSubscribe",
            cascade = CascadeType.ALL)
    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "COURSE_ATTENDERS",
            joinColumns = @JoinColumn(name = "id_course"))
    @Column(name = "eval")
    @MapKeyJoinColumn(name = "id_user", referencedColumnName = "id_user")
    public Map<User, Integer> getEvalMap() {
        return evalMap;
    }

    public void setEvalMap(Map<User, Integer> evalMap) {
             this.evalMap = evalMap;
    }

    @Override
    public int hashCode() {
        return getId()*11+11;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Course))
            return false;
        Course objCourse = (Course) obj;
        return this.getId() == objCourse.getId();
    }

}
