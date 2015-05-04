package by.mogilev.service;

/**
 * Created by akiseleva on 29.04.2015.
 */

import by.mogilev.model.Course;
import by.mogilev.model.Notification;
import by.mogilev.model.User;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Set;

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


    public void sendEmail(int id_course, final Notification NOTIFICATION, final InternetAddress[] emails, final String userName) {
//        final Course course = courseService.getCourse(id_course);
//        final User curr_user = userService.getUser(userName);
//
//        MimeMessagePreparator preparator = new MimeMessagePreparator() {
//            @SuppressWarnings({"rawtypes", "unchecked"}) //rawtypes - ������������ ��������������, ����������� � ������������� ������������� ����� � ��������;
//            public void prepare(MimeMessage mimeMessage) throws Exception {
//                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
//                message.setTo(emails);
////                message.setFrom(course.getLector().getEmail());
//                message.setFrom("alina@gorad.by");
//                message.setSubject("Courses Notification");
//                Map model = new HashMap();
//                model.put("message", course);
//                model.put("user", curr_user);
//                String text = VelocityEngineUtils.mergeTemplateIntoString(
//                        velocityEngine, "mailTemplates/" + NOTIFICATION + ".vm", "UTF-8", model);
//
//                message.setText(text, true);
//            }
//
//        };
//        mailSender.send(preparator);
    }

    @Override
    public InternetAddress[] getRecipient(Course course) throws AddressException {
        Set<User> subscr = course.getSubscribers();
        InternetAddress[] emails= new InternetAddress[subscr.size()];

        int i = 0;
        for (User u : subscr) {
            emails[i] = new InternetAddress(u.getEmail());
            i++;
        }
        return emails;

    }
}




