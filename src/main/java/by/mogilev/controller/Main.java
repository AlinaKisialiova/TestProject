package by.mogilev.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
* Created by Администратор on 31.03.2015.
*/
public class Main {
    public static void main(String args[]) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream("C:\\test.pdf"));
        document.open();

            Anchor anchorTarget = new Anchor("First page of the document.");
            anchorTarget.setName("BackToTop");

            Paragraph paragraph1 = new Paragraph();
            paragraph1.setSpacingBefore(50);

            paragraph1.add(anchorTarget);
            document.add(paragraph1);

            document.add(new Paragraph("List of course the Training Center!",
                    FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,	new CMYKColor(0, 255, 0, 0))));


            Paragraph title1 = new Paragraph("Chapter 1",
                    FontFactory.getFont(FontFactory.HELVETICA,
                            18, Font.BOLDITALIC, new CMYKColor(0, 255, 255,17)));
            Chapter chapter1 = new Chapter(title1, 1);

            chapter1.setNumberDepth(0);
            Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1",

                    FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,

                            new CMYKColor(0, 255, 255,17)));

            Section section1 = chapter1.addSection(title11);

            Paragraph someSectionText = new Paragraph("This text comes as part of section 1 of chapter 1.");

            section1.add(someSectionText);

            someSectionText = new Paragraph("Following is a 3 X 2 table.");



            section1.add(someSectionText);

            document.add(chapter1);
            document.close();



        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
