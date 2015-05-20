package by.mogilev.service;

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.Notification;
import by.mogilev.model.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

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
     * @param
     * @return
     * @throws AddressException
     */
    public InternetAddress[] getRecipient(Set<User> users) throws AddressException;

    /**
     * Get id for send error notifications
     * @param
     * @return
        */
    public int getIdCourseFromUri(HttpServletRequest request);

}
