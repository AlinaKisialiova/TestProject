package by.mogilev.service;

import by.mogilev.model.Course;
import by.mogilev.model.Notification;
import by.mogilev.model.User;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by Администратор on 29.04.2015.
 */
public interface MailService {
    public void sendEmail(int id_course, Notification notification, InternetAddress[] emails, String userName);
    public InternetAddress[] getRecipient(Course course) throws AddressException;
}
