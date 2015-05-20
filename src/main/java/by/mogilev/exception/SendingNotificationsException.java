package by.mogilev.exception;

import by.mogilev.model.Notification;
import by.mogilev.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by ������������� on 16.05.2015.
 */


public class SendingNotificationsException extends TrainingCenterException {
    @Autowired
    private MailService mailService;

    private String exceptionMessage;

    public String toString() {
        return exceptionMessage;
    }

    public void sendExceptionEmail(int id, InternetAddress[] email, String userName) throws NotFoundUserException, AddressException, NotFoundCourseException {

        mailService.sendEmail(id, Notification.ADDRESS_EXCEPTION_MESSAGE, email, userName, exceptionMessage);

    }

}
