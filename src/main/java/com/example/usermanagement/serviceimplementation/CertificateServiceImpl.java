package com.example.usermanagement.serviceimplementation;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.usermanagement.dto.CertificateDTO;
import com.example.usermanagement.dto.ProgressDTO;
import com.example.usermanagement.entity.Certificate;
import com.example.usermanagement.entity.Course;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.repository.CertificateRepository;
import com.example.usermanagement.repository.CourseRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.CertificateService;
import com.example.usermanagement.service.ProgressService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProgressService progressService;

    //CREATE / FETCH CERTIFICATE 

    @Override
    public Certificate generateCertificate(Long userId, Long courseId) {

        // If certificate already exists → return it (DO NOT recheck progress)
        return certificateRepository
            .findByUser_IdAndCourse_Id(userId, courseId)
            .orElseGet(() -> {

                ProgressDTO progress =
                    progressService.getCourseProgress(userId, courseId);

                if (progress.getPercentage() < 100) {
                    throw new RuntimeException("Course not completed yet");
                }

                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new RuntimeException("Course not found"));

                Certificate cert = new Certificate(user, course);
                return certificateRepository.save(cert);
            });
    }

    //PDF GENERATION 

    @Override
    public byte[] generateCertificatePdf(Long userId, Long courseId) {

        // Ensure certificate exists (or create)
        Certificate cert = generateCertificate(userId, courseId);

        User user = cert.getUser();
        Course course = cert.getCourse();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            //FONTS
            Font titleFont = new Font(Font.HELVETICA, 26, Font.BOLD);
            Font subTitleFont = new Font(Font.HELVETICA, 16);
            Font nameFont = new Font(Font.HELVETICA, 20, Font.BOLD);
            Font courseFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font smallFont = new Font(Font.HELVETICA, 12);

            //TITLE
            Paragraph title = new Paragraph("Certificate of Completion", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n\n"));

            //BODY
            Paragraph body1 = new Paragraph("This is to certify that", subTitleFont);
            body1.setAlignment(Element.ALIGN_CENTER);
            document.add(body1);

            document.add(new Paragraph("\n"));

            Paragraph userName =
                    new Paragraph(user.getUserName(), nameFont);
            userName.setAlignment(Element.ALIGN_CENTER);
            document.add(userName);

            document.add(new Paragraph("\n"));

            Paragraph body2 =
                    new Paragraph("has successfully completed the course", subTitleFont);
            body2.setAlignment(Element.ALIGN_CENTER);
            document.add(body2);

            document.add(new Paragraph("\n"));

            Paragraph courseTitle =
                    new Paragraph(course.getTitle(), courseFont);
            courseTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(courseTitle);

            document.add(new Paragraph("\n\n"));

            //FOOTER
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd MMM yyyy");

            Paragraph issuedDate = new Paragraph(
                    "Issued on: " + LocalDate.now().format(formatter),
                    smallFont
            );
            issuedDate.setAlignment(Element.ALIGN_CENTER);
            document.add(issuedDate);

            document.add(new Paragraph("\n"));

            Paragraph code = new Paragraph(
                    "Certificate Code: " + cert.getCertificateCode(),
                    smallFont
            );
            code.setAlignment(Element.ALIGN_CENTER);
            document.add(code);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating certificate PDF", e);
        }

        return out.toByteArray();
    }
    @Override
    public List<CertificateDTO> getMyCertificateDTOs(Long userId) {

        List<Certificate> certificates =
                certificateRepository.findByUser_Id(userId);

        List<CertificateDTO> response = new ArrayList<>();

        for (Certificate cert : certificates) {
            response.add(new CertificateDTO(
                    cert.getCourse().getId(),   
                    cert.getCourse().getTitle(),
                    cert.getCertificateCode(),
                    cert.getIssuedAt()
            ));
        }

        return response;
    }


}
