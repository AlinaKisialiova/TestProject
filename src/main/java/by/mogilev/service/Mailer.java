package by.mogilev.service;

/**
 * Created by akiseleva on 29.04.2015.
 */

import by.mogilev.model.Course;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.io.StringWriter;


public class Mailer {
    @Autowired
    private CourseService courseService;

    private MailSender mailSender;
    private VelocityEngine velocityEngine;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }


    public void courseAnnouncementMail(String courseName){
        SimpleMailMessage message = new SimpleMailMessage();

        Course course = courseService.getCourseByName(courseName);

        message.setFrom(course.getLector().getEmail());
        message.setTo("aflamma@yandex.ru");
        message.setSubject("Course Announcement ");

        Template template = velocityEngine.getTemplate("mailTemplates/CourseAnnouncement.vm");

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("lector", course.getLector().getName());
        velocityContext.put("category", course.getCategory());
        velocityContext.put("nameCourse", course.getNameCourse());
        velocityContext.put("description", course.getDescription());
        velocityContext.put("links", course.getLinks());
        velocityContext.put("duration", course.getDuration());
        velocityContext.put("courseLink", "http://project/courseDetails/"+course.getId());

        StringWriter stringWriter = new StringWriter();

        template.merge(velocityContext, stringWriter);

        message.setText(stringWriter.toString());

        mailSender.send(message);
    }

}
