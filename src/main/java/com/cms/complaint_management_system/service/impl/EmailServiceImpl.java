package com.cms.complaint_management_system.service.impl;

import com.cms.complaint_management_system.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;


    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }


    @Override
    public void sendEmail(String toEmail, String name, String complaintId, String categoryName, String departmentName, String date){

        Context context = new Context();

        context.setVariable("name", name);
        context.setVariable("complaintId", complaintId);
        context.setVariable("categoryName", categoryName);
        context.setVariable("departmentName", departmentName);
        context.setVariable("date", date);

        String htmlContent = templateEngine.process("complaint-confirmation", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject("Complaint Confirmation");
            mimeMessageHelper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email to:"+toEmail);
        }
    }
}
