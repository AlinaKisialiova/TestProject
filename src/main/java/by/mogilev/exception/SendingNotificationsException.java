package by.mogilev.exception;

import by.mogilev.model.Course;
import by.mogilev.model.Notification;
import by.mogilev.service.CourseService;
import by.mogilev.service.MailService;
import by.mogilev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by ������������� on 16.05.2015.
 */


public class SendingNotificationsException extends TrainingCenterException {
    @Autowired
    private MailService mailService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    private Course course;
    private String exceptionMessage;

    public SendingNotificationsException(Course course, String exceptionMessage) {
        this.course = course;
    }

    public String toString() {
        return exceptionMessage;
    }

    public void sendExceptionEmail(InternetAddress[] email, String userName) throws NotFoundUserException, AddressException, NotFoundCourseException {

        mailService.sendEmail(course.getId(), Notification.ADDRESS_EXCEPTION_MESSAGE, email, userName, exceptionMessage);

    }

}
