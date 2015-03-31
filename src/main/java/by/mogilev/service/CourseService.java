package by.mogilev.service;

import by.mogilev.model.Course;
import com.itextpdf.text.DocumentException;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Администратор on 21.03.2015.
 */
public interface CourseService {
    boolean isOwner(int idCourse, HttpSession session);

    public void remidEv(int id, int grade);
    Map<String, String> getCategotyMap();
    /**
     *
     * Method out in pdf-file list of courses
     *
     */
    public void outInPdfAllCourse() throws IOException, DocumentException;

}
