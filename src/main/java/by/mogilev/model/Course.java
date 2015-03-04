package by.mogilev.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akiseleva on 26.02.2015.
 */
public class Course {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String category;
    private String nameCourse;
    private String nameLector;
    private String duration;
    private String description;
    private String links;
    private  int numbOfSubscribers;
    private int numbOfAttendee;
//    private final int MAXNUMBOFATTENDEE=15; v zapis na kurs
//    private final int MINNUMBOFATTENDEE=30;
    private int evaluation;
    private boolean delivered;
    private List<Participant> attendee;
    public Course() {}
    public Course(int id, String category, String nameCourse, String nameLector, String duration, String description,
                  String links, int numbOfSubscribers, int numbOfAttendee, int evaluation, boolean delivered,
                  List<Participant> attendee) {
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

        public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public String getNameLector() {
        return nameLector;
    }

    public void setNameLector(String nameLector) {
        this.nameLector = nameLector;
    }

    public String getDuration() {
        return duration;

    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public int getNumbOfSubscribers() {
        return numbOfSubscribers;
    }

    public void setNumbOfSubscribers(int numbOfSubscribers) {
        this.numbOfSubscribers = numbOfSubscribers;
    }

    public int getNumbOfAttendee() {
        return numbOfAttendee;
    }

    public void setNumbOfAttendee(int numbOfAttendee) {
        this.numbOfAttendee = numbOfAttendee;
    }

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

    public List<Participant> getAttendee() {
        return attendee;
    }

    public void setAttendee(List<Participant> attendee) {
        this.attendee = attendee;
    }




}
