package by.mogilev.service;

/**
 * Created by akiseleva on 29.04.2015.
 */

import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.Course;
import by.mogilev.model.Notification;
import by.mogilev.model.User;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VelocityEngine velocityEngine;


    public void sendEmail(int id_course, final Notification NOTIFICATION, final InternetAddress[] emails,
                          final String userName, final String errEmail) throws NotFoundUserException, NotFoundCourseException {
        if (id_course < 1) throw new NotFoundCourseException();
        if (userName == null) throw new NotFoundUserException();

        final Course course = courseService.getCourse(id_course);
        final User curr_user = userService.getUser(userName);

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @SuppressWarnings({"rawtypes", "unchecked"})
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(emails);
                message.setFrom("alina@gorad.by");
                message.setSubject("Courses Notification");
                Map model = new HashMap();
                model.put("message", course);
                model.put("user", curr_user);
                model.put("errEmail", errEmail);
                String text = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, "mailTemplates/" + NOTIFICATION + ".vm", "UTF-8", model);

                message.setText(text, true);
            }

        };
        mailSender.send(preparator);
    }


    @Override
    public InternetAddress[] getRecipient(Set<User> users) throws AddressException {
    List<InternetAddress> addressList = new ArrayList<InternetAddress>(users.size());
      for (User user : users)
          addressList.add(new InternetAddress(user.getEmail()));

        return (InternetAddress[]) addressList.toArray();
    }


}




