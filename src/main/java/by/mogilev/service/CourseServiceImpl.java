package by.mogilev.service;

import by.mogilev.dao.CourseDAO;
import by.mogilev.exception.IsNotOwnerException;
import by.mogilev.exception.NotFoundCourseException;
import by.mogilev.exception.NotFoundUserException;
import by.mogilev.model.Course;
import by.mogilev.model.CourseStatus;
import by.mogilev.model.Notification;
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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Администратор on 21.03.2015.
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final static String  NAME_FILE = "listOfCourses";
    private final static String IMAGE_FOR_PDF = "D:\\1.JPG";

    CourseServiceImpl() {
    }

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private MailService mailService;

    @Override
    public boolean isOwner(int id_course, String userName) throws NotFoundCourseException, NotFoundUserException {

        if (userName == null) throw  new NotFoundUserException();
        Course checkCourse = courseDAO.getCourse(id_course);
        if (checkCourse == null) throw  new NotFoundCourseException();
        return checkCourse.getLector().getUsername().equals(userName);
    }


    @Override
    public void outInPdfAllCourse(HttpServletResponse response, List<Course> courses) throws IOException, DocumentException {

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
        fileInputStream.close();

    }

    @Override
    public void outInExcelAllCourse(HttpServletResponse response, List<Course> courses) throws IOException {

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
    public void remidEv(int id, User user, int grade) throws AddressException, NotFoundCourseException, NotFoundUserException {

        if (user == null) throw new NotFoundUserException();
        Course changeEvalCourse = courseDAO.getCourse(id);
        if (changeEvalCourse == null) throw new NotFoundCourseException();

        Map<User, Integer> mapEval = changeEvalCourse.getEvalMap();
        if(mapEval.containsKey(user))
            mapEval.replace(user, grade);
        else
        mapEval.put(user, grade);

        changeEvalCourse.setEvalMap(mapEval);
        courseDAO.updateCourse(changeEvalCourse);

        int evaluat = 0;
        int count = 0;
        for (Map.Entry<User, Integer> entry : changeEvalCourse.getEvalMap().entrySet()) {
            if (entry.getValue() != null || entry.getValue() != 0) {
                evaluat += entry.getValue();
                count++;
            }
        }

        changeEvalCourse.setEvaluation(evaluat / count);
        courseDAO.updateCourse(changeEvalCourse);

    }



    @Override
    public List<Course> getAllCourse() {
        return courseDAO.getAllCourse();
    }

    @Override
    public void deleteCourse(int id, String userName) throws AddressException, NotFoundCourseException, NotFoundUserException, IsNotOwnerException {

        if(userName == null) throw new NotFoundUserException();

        Course course = courseDAO.getCourse(id);
        if (course == null) throw new NotFoundCourseException();
        if (!isOwner(id, userName)) throw new IsNotOwnerException();
        courseDAO.deleteCourse(course);

        InternetAddress[] emails = mailService.getRecipient(course.getSubscribers());
        mailService.sendEmail(id, Notification.COURSE_DELETE, emails, userName, "");
    }

    @Override
    public Course getCourse(int id) throws NotFoundCourseException {
        if (courseDAO.getCourse(id) == null) throw  new NotFoundCourseException();
            else
        return courseDAO.getCourse(id);
    }

    @Override
    public void registerCourse(Course course, String nameLector) throws AddressException, NotFoundUserException, NotFoundCourseException {
        if (nameLector == null) throw new NotFoundUserException();
        courseDAO.registerCourse(course, nameLector);
        Course newCourse = courseDAO.getCourseByNameDao(course.getNameCourse());
        if (newCourse == null) throw new NotFoundCourseException();
        int id = newCourse.getId();
        InternetAddress[] emails= new InternetAddress[]{
                new InternetAddress("aflamma@yandex.ru")};
        mailService.sendEmail(id, Notification.COURSE_ANNOUNCEMENT, emails, nameLector,"");

    }

    @Override
    public void updateCourse(Course updCourse) throws AddressException, NotFoundCourseException, NotFoundUserException {

        if (updCourse == null) throw  new NotFoundCourseException();
        courseDAO.updateCourse(updCourse);
    InternetAddress [] emails = new InternetAddress[2];
        emails[0] = new InternetAddress("aflamma@yandex.ru");
        emails[1] = new InternetAddress("alina@gorad.by");
        mailService.sendEmail(updCourse.getId(), Notification.COURSE_UPDATE, emails, "alex Ivanov","");

    }

    @Override
    public List<Course> getCourseForDepartmentManager() {
        List<Course> coursesForApprove = getAllCourse();
        Iterator<Course> c = coursesForApprove.iterator();
       while(c.hasNext()) {
            if (!CourseStatus.NOT_APPROVE.equals(c.next().getCourseStatus()))
               c.remove();
        }
        return coursesForApprove;

    }

    @Override
    public List<Course> getCourseForKnowledgeManagerManager() {
        List<Course> coursesForApprove = getAllCourse();
        Iterator<Course> c = coursesForApprove.iterator();
        while(c.hasNext()) {
            if (!CourseStatus.APPROVE_DEPARTMENT_MANAGER.equals(c.next().getCourseStatus()))
                c.remove();
        }
        return coursesForApprove;
    }

    @Override
    public List<Course> getListForAttendee(List<Course> subcrCourse) throws NotFoundUserException {
        List<Course> coursesForList = new ArrayList<Course>();
        for (Course c : subcrCourse) {
            if (c.getAttenders().size() < Course.MAX_COUNT_ATT && (CourseStatus.DELIVERED.equals(c.getCourseStatus())
                    || CourseStatus.APPROVE_KNOWLEDGE_MANAGER.equals(c.getCourseStatus()))) {
                coursesForList.add(c);
            }
        }
        return coursesForList;


    }

    @Override
    public List<Course> getSortList(List<Course> courseAtt, String selectCategory) throws NotFoundUserException {
        List<Course> coursesForListSort = new ArrayList<Course>();
        if ("All".equals(selectCategory))
            coursesForListSort = courseAtt;
        else {

            for (Course c : courseAtt) {
                if (c.getCategory().equals(selectCategory))
                    coursesForListSort.add(c);
            }
        }
        return coursesForListSort;

    }

}
