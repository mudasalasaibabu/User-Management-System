package com.example.usermanagement.serviceimplementation;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.usermanagement.dto.CertificateDTO;
import com.example.usermanagement.entity.Certificate;
import com.example.usermanagement.entity.Course;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.entity.UserCourse;
import com.example.usermanagement.repository.CertificateRepository;
import com.example.usermanagement.repository.CourseRepository;
import com.example.usermanagement.repository.UserCourseRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.CertificateService;
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
    private UserCourseRepository  userCourseRepository;

//    @Autowired
//    private ProgressService progressService;
    

    //CREATE / FETCH CERTIFICATE 
//
//    @Override
//    public Certificate generateCertificate(Long userId, Long courseId) {
//
//        // If certificate already exists → return it (DO NOT recheck progress)
//        return certificateRepository
//            .findByUser_IdAndCourse_Id(userId, courseId)
//            .orElseGet(() -> {
//
//                ProgressDTO progress =
//                    progressService.getCourseProgress(userId, courseId);
//
//                if (progress.getPercentage() < 100) {
//                    throw new RuntimeException("Course not completed yet");
//                }
//
//                User user = userRepository.findById(userId)
//                        .orElseThrow(() -> new RuntimeException("User not found"));
//
//                Course course = courseRepository.findById(courseId)
//                        .orElseThrow(() -> new RuntimeException("Course not found"));
//
//                Certificate cert = new Certificate(user, course);
//                return certificateRepository.save(cert);
//            });
//    }

    @Transactional
    @Override
    public Certificate generateCertificate(Long userId, Long courseId) {

        // If already exists, return it
        Optional<Certificate> existing =
                certificateRepository.findByUser_IdAndCourse_Id(userId, courseId);

        if (existing.isPresent()) {
            return existing.get();
        }

        // Check if course is completed
        Optional<UserCourse> uc =
                userCourseRepository.findByUser_IdAndCourse_Id(userId, courseId);

        if (uc.isEmpty() || !uc.get().getCompleted()) {
            throw new RuntimeException("Course not completed yet");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Certificate cert = new Certificate(user, course);
        return certificateRepository.save(cert);
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
    public List<Certificate> getMyCertificates(Long userId) {

        // Get only completed courses
        List<UserCourse> completedCourses =
                userCourseRepository.findByUser_IdAndCompletedTrue(userId);

        List<Certificate> result = new ArrayList<>();

        for (int i = 0; i < completedCourses.size(); i++) {

            UserCourse uc = completedCourses.get(i);

            Optional<Certificate> cert =
                    certificateRepository.findByUser_IdAndCourse_Id(
                            userId,
                            uc.getCourse().getId()
                    );

            if (cert.isPresent()) {
                result.add(cert.get());
            }
        }

        return result;
    }

    @Override
    public CertificateDTO getCertificateForCourse(Long userId, Long courseId) {

        List<UserCourse> completedCourses =
                userCourseRepository.findByUser_IdAndCompletedTrue(userId);

        for (int i = 0; i < completedCourses.size(); i++) {
            UserCourse uc = completedCourses.get(i);

            if (uc.getCourse().getId().equals(courseId)) {

                Certificate cert = generateCertificate(userId, courseId);

                return new CertificateDTO(
                        cert.getCourse().getId(),
                        cert.getCourse().getTitle(),
                        cert.getCertificateCode(),
                        cert.getIssuedAt()
                );
            }
        }

        throw new RuntimeException("Course not completed, certificate not available");
    }


}
