package by.mogilev.service;

import by.mogilev.model.Course;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by Администратор on 21.03.2015.
 */
public interface CourseService {
    boolean isOwner(int idCourse, HttpSession session);
    public void remidEv(int id, int grade);
    Map<String, String> getCategotyMap();
}
