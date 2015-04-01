package by.mogilev.service;

import by.mogilev.dao.CourseDAO;
import by.mogilev.model.Course;
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

import javax.servlet.http.HttpSession;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public void outInPdfAllCourse() throws IOException, DocumentException {

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream("D:\\listOfCourses.pdf"));
        document.open();
        Paragraph paragraph1 = new Paragraph("This is list of courses a Training Center",
                FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,
                        new CMYKColor(255, 255, 0, 0)));
        paragraph1.setSpacingBefore(50);
        document.add(paragraph1);

        PdfPTable t = new PdfPTable(7);

        t.setSpacingBefore(25);
        t.setSpacingAfter(25);

        PdfPCell c1 = new PdfPCell(new Phrase("Lector Name",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));

        t.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Course Name",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Phrase("Course Category",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Phrase("Subscribed",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Phrase("Participated",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c5);
        PdfPCell c6 = new PdfPCell(new Phrase("Delivered Course",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c6);
        PdfPCell c7 = new PdfPCell(new Phrase("Evaluation",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD,
                new CMYKColor(100, 78, 0, 44))));
        t.addCell(c7);

        java.util.List<Course> courses=courseDAO.getAllCourse();

        for (Course c : courses)
        {
            t.addCell(c.getLector().getName());
            t.addCell(c.getNameCourse());
            t.addCell(c.getCategory());
            t.addCell(String.valueOf(c.getSubscribers().size()));
            t.addCell(String.valueOf(c.getAttenders().size()));
            t.addCell(String.valueOf(c.isDelivered()));
            t.addCell(String.valueOf(c.getEvaluation()));

        }
        document.add(t);

        Image image1 = Image.getInstance("D:\\1.JPG");
        image1.scaleAbsolute(400f, 225f);

        document.add(image1);





        document.close();
    }

    @Override
    public void outInExcelAllCourse() throws IOException {

        final String[] titles = {
                "Lector Name", "Course Name", "Course Category", "Subscribed", "Participated", "Delivered Course", "Evaluation"};
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("List of Courses");
        int numbRow=0;

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

        java.util.List<Course> courses=courseDAO.getAllCourse();

        for (Course c : courses)
        {

         String[] data =
                {c.getLector().getName(), c.getNameCourse(), c.getCategory(), String.valueOf(c.getSubscribers().size()),
                String.valueOf(c.getAttenders().size()), String.valueOf(c.isDelivered()), String.valueOf(c.getEvaluation())};

            Row row = sheet.createRow(numbRow);
                for (int j = 0; j < titles.length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(data[j]);
                }
numbRow++;


    }

        String file = "D:\\listOfCourse.xls";
        FileOutputStream out = new FileOutputStream(file);
        wb.write(out);
        out.close();
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
