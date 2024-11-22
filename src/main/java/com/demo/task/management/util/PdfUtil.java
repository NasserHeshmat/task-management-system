package com.demo.task.management.util;

import com.demo.task.management.entity.CourseSchedule;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PdfUtil {

    public byte[] generateCourseSchedulePdf(List<CourseSchedule> courseSchedules, String courseName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 28);
            contentStream.setNonStrokingColor(Color.black);
            contentStream.newLineAtOffset(25, 700);
            contentStream.showText("Course Schedule :   "+courseName);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 18);
            contentStream.newLineAtOffset(25, 665);
            contentStream.setNonStrokingColor(Color.darkGray);
            contentStream.showText("Schedule Time                       Instructor");
            contentStream.newLineAtOffset(25, 665);
            contentStream.endText();

            float yPosition = 640;

            for (CourseSchedule schedule : courseSchedules) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 16);
                contentStream.newLineAtOffset(25, yPosition);
                contentStream.setNonStrokingColor(Color.darkGray);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm a");
                String readableDate = schedule.getSchedule().format(formatter);
                contentStream.showText(readableDate + "                 " + schedule.getInstructor());
                contentStream.endText();
                yPosition -= 20;
            }

            contentStream.close();
            document.save(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
