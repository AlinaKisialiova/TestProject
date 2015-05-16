package by.mogilev.service;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.Course;
import by.mogilev.model.Notification;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by ������������� on 29.04.2015.
 */
public interface MailService {
    /**
     * Method send notifications on email student course
     * @param id_course
     * @param notification
     * @param emails
     * @param userName
     */
    public void sendEmail(int id_course, Notification notification, InternetAddress[] emails, String userName, String errEmail)
            throws NotFoundUserException, NotFoundCourseException;

    /**
     * Get list of email for send notifications
     * @param course
     * @return
     * @throws AddressException
     */
    public InternetAddress[] getRecipientSubsc(Course course) throws AddressException;

    /**
     * Get list of email for send notifications
     * @param course
     * @return
     * @throws AddressException
     */
    public InternetAddress[] getRecipientAtt(Course course) throws AddressException;
}
