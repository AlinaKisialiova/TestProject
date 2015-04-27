package by.mogilev.service;

import by.mogilev.dao.CourseDAO;
import by.mogilev.dao.UserDAO;
import by.mogilev.model.Course;
import by.mogilev.model.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Администратор on 21.03.2015.
 */
@Service
public class CourseServiceImpl implements CourseService {

    final String NAME_FILE = "listOfCourses";
    final String IMAGE_FOR_PDF = "D:\\1.JPG";

    CourseServiceImpl() {
    }

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;

    @Override
    public boolean isOwner(int idCourse, HttpSession session) {
//        Course checkCourse = courseDAO.getCourse(idCourse);
//        User user = (User)session.getAttribute("user");
//        if(checkCourse.getLector().getName().equals(user.getName()))
        return true;
//        return false;
    }


    @Override
    public void outInPdfAllCourse(HttpServletResponse response) throws IOException, DocumentException {

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(NAME_FILE + ".pdf"));
        document.open();
        Paragraph paragraph1 = new Paragraph("This is list of courses a Training Center",
                FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,
                        new CMYKColor(255, 255, 0, 0)));
        paragraph1.setSpacingBefore(50);
        document.add(paragraph1);

        PdfPTable t = new PdfPTable(7);

        t.setSpacingBefore(25);
        t.setSpacingAfter(25);

        PdfPCell c1 = new PdfPCell(new Phrase("Lector Name", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));

        t.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Course Name", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Phrase("Course Category", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Phrase("Subscribed", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Phrase("Participated", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c5);
        PdfPCell c6 = new PdfPCell(new Phrase("Delivered Course", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c6);
        PdfPCell c7 = new PdfPCell(new Phrase("Evaluation", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c7);

        java.util.List<Course> courses = courseDAO.getAllCourse();

        for (Course c : courses) {
            t.addCell(c.getLector().getName());
            t.addCell(c.getNameCourse());
            t.addCell(c.getCategory());
            t.addCell(String.valueOf(c.getSubscribers().size()));
            t.addCell(String.valueOf(c.getAttenders().size()));
            t.addCell(String.valueOf(c.getCourseStatus()));
            t.addCell(String.valueOf(c.getEvaluation()));

        }
        document.add(t);
        if (new File(IMAGE_FOR_PDF).exists()) {
            Image image1 = Image.getInstance(IMAGE_FOR_PDF);
            image1.scaleAbsolute(400f, 225f);
            document.add(image1);
        }
        document.close();

        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + NAME_FILE + ".pdf");
        FileInputStream fileInputStream = new FileInputStream(NAME_FILE + ".pdf");

        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            out.write(bytes);
        }
        out.close();


    }

    @Override
    public void outInExcelAllCourse(HttpServletResponse response) throws IOException {

        final String[] titles = {
                "Lector Name", "Course Name", "Course Category", "Subscribed", "Participated", "Delivered Course", "Evaluation"};
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("List of Courses");
        int numbRow = 0;

        Row headerRow = sheet.createRow(numbRow);
        numbRow++;
        Cell cellHeader = headerRow.createCell(numbRow);
        cellHeader.setCellValue("List of Courses of Training Center");


        Row titleRow = sheet.createRow(numbRow);
        numbRow++;
        for (int i = 0; i < titles.length; i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(titles[i]);
        }

        java.util.List<Course> courses = courseDAO.getAllCourse();

        for (Course c : courses) {

            String[] data =
                    {c.getLector().getName(), c.getNameCourse(), c.getCategory(), String.valueOf(c.getSubscribers().size()),
                            String.valueOf(c.getAttenders().size()), String.valueOf(c.getCourseStatus()), String.valueOf(c.getEvaluation())};

            Row row = sheet.createRow(numbRow);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(data[j]);
            }
            numbRow++;


        }

        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/xml");
        response.setHeader("Content-Disposition", "attachment; filename=" + NAME_FILE + ".xls");
        wb.write(out);
        out.close();
    }

    @Override
    public Map<String, String> getCategotyMap() {
        Map<String, String> categoryMap = new HashMap<String, String>();
        categoryMap.put("Project Management", "Project Management");
        categoryMap.put("Development", "Development");
        return categoryMap;
    }


    @Override
    public void remidEv(int id, User user, int grade) {
        Course changeEvalCourse = courseDAO.getCourse(id);

        Map<User, Integer> mapEval = changeEvalCourse.getEvalMap();
        if(mapEval.containsKey(user))
            mapEval.replace(user, grade);
        else
        mapEval.put(user, grade);
        changeEvalCourse.setEvalMap(mapEval);
        courseDAO.updateCourse(changeEvalCourse);

        int evaluat = 0;
        for (Map.Entry<User, Integer> entry : changeEvalCourse.getEvalMap().entrySet()) {
            if (entry.getValue() != null)
            evaluat += entry.getValue();
        }

        changeEvalCourse.setEvaluation(evaluat / changeEvalCourse.getEvalMap().size());
        courseDAO.updateCourse(changeEvalCourse);
    }

    @SuppressWarnings("unchecked")
    @Override
    public java.util.List<Course> getSelected(String category) {
        if (category == null) category = "All";
        if (category.equals("All"))
            return courseDAO.getAllCourse();
        else
            return courseDAO.getSelectedDao(category);
    }

    @Override
    public List<Course> getAllCourse() {
        return courseDAO.getAllCourse();
    }

    @Override
    public void deleteCourse(int id, String userName) {
        if (id < 1) throw new NullPointerException("Id course for delete is null");

        Course course = courseDAO.getCourse(id);
       if (userDAO.getUser(userName).getId() != course.getLector().getId())
           return;

        courseDAO.deleteCourse(course);
    }

    @Override
    public Course getCourse(int id) {
        if (id < 1) throw new NullPointerException("Id course is null in getCourse()");
        return courseDAO.getCourse(id);
    }

    @Override
    public void registerCourse(Course course, String nameLector) {
        courseDAO.registerCourse(course, nameLector);
    }

    @Override
    public void updateCourse(int id, Course updCourse) {
        Course editCourse = getCourse(id);

        editCourse.setCategory(updCourse.getCategory());
        editCourse.setNameCourse(updCourse.getNameCourse());
        editCourse.setDescription(updCourse.getDescription());
        editCourse.setLinks(updCourse.getLinks());
        editCourse.setDuration(updCourse.getDuration());
        editCourse.setEvaluation(0);

        courseDAO.updateCourse(editCourse);
    }

    @Override
    public boolean startCourse(int id, String userName) {
        if (id < 1) throw new NullPointerException("Id course is null in startCourse()");
//cheharda
        Course course = courseDAO.getCourse(id);
        if (course.getAttenders().size() >= Course.MIN_COUNT_SUBSCR && userDAO.getUser(userName) == course.getLector()) {

            courseDAO.updateCourse(course);
            return true;
        }
        return false;
    }


}
