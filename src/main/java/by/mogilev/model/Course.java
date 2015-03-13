package by.mogilev.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akiseleva on 26.02.2015.
 */

@Entity
@Table(name ="COURSE")
public class Course {
    private int id;
    private String category;
    private String nameCourse;
    private String nameLector;
    private String duration;
    private String description;
    private String links;
    private  int numbOfSubscribers;
    private int numbOfAttendee;
    private int evaluation;
    private boolean delivered;
    private List<User> attendee;

    public Course(String category, String nameCourse, String description, String links, String duration, String nameLector) {
        this.category=category;
        this.nameCourse=nameCourse;
        this.description=description;
        this.links=links;
        this.duration=duration;
        this.nameLector=nameLector;

    }
    public Course() {}
    public Course(int id, String category, String nameCourse, String nameLector, String duration, String description,
                  String links, int numbOfSubscribers, int numbOfAttendee, int evaluation, boolean delivered,
                  List<User> attendee) {
        this.id=id;
        this.category = category;
        this.nameCourse = nameCourse;
        this.nameLector = nameLector;
        this.duration = duration;
        this.description = description;
        this.links = links;
        this.numbOfSubscribers = numbOfSubscribers;
        this.numbOfAttendee = numbOfAttendee;
        this.evaluation = evaluation;
        this.delivered = delivered;
        this.attendee = attendee;
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

    @Column(name = "nameLector")
    public String getNameLector() {
        return nameLector;
    }
    public void setNameLector(String nameLector) {
        this.nameLector = nameLector;
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

    @Column(name = "numbOfSubscribers")
    public int getNumbOfSubscribers() {
        return numbOfSubscribers;
    }
    public void setNumbOfSubscribers(int numbOfSubscribers) {
        this.numbOfSubscribers = numbOfSubscribers;
    }

    @Column(name = "numbOfAttendee")
    public int getNumbOfAttendee() {
        return numbOfAttendee;
    }
    public void setNumbOfAttendee(int numbOfAttendee) {
        this.numbOfAttendee = numbOfAttendee;
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "course")
    public List<User> getAttendee() {
        return attendee;
    }
    public void setAttendee(List<User> attendee) {
        this.attendee = attendee;
    }




}
