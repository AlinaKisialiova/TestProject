package by.mogilev.service;

import by.mogilev.dao.CourseDAO;
import by.mogilev.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Администратор on 21.03.2015.
 */
@Service
public class CourseServiceImpl implements CourseService {

    CourseServiceImpl(){}

    @Autowired
    private CourseDAO courseDAO;

    @Override
    public boolean isOwner(int idCourse, HttpSession session) {
//        Course checkCourse = courseDAO.getCourse(idCourse);
//        User user = (User)session.getAttribute("user");
//        if(checkCourse.getLector().getName().equals(user.getName()))
        return true;
//        return false;
    }
    @Override
    public Map<String, String> getCategotyMap() {
        Map<String,String> categoryMap = new HashMap<String,String>();
        categoryMap.put("Project Management", "Project Management");
        categoryMap.put("Development", "Development");
        return categoryMap;
    }

    @Override
    public void remidEv(int id, int grade) {
        Course changeEvalCourse = courseDAO.getCourse(id);
        changeEvalCourse.setEvaluation(grade);
       courseDAO.updateCourse(changeEvalCourse);


    }


}
