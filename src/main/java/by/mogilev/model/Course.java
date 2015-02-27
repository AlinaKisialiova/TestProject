package by.mogilev.model;

import java.util.List;

/**
 * Created by akiseleva on 26.02.2015.
 */
public class Course {
    private String category;
    private String nameCourse;
    private String nameLector;
    private String duration;
    private String description;
    private String links;
    private  int numbOfSubscribers;
    private int numbOfAttendee;
    private final int MAXNUMBOFATTENDEE=15;
    private final int MINNUMBOFATTENDEE=30;
    private int evaluation;
    private boolean delivered;
    private List<Participant> attendee;

   public Course (String category, String nameCourse, String description,String links, String duration ){
       this.category=category;
       this.nameCourse=nameCourse;
       this.description=description;
       this.links=links;
       this.duration=duration;
   }

}
