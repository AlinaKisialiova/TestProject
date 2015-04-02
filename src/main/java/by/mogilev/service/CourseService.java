package by.mogilev.service;

import com.itextpdf.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    public void outInPdfAllCourse(HttpServletResponse response) throws IOException, DocumentException;
    /**
     *
     * Method out in Excel list of courses
     *
     */
    public void outInExcelAllCourse(HttpServletResponse response) throws IOException;

}
