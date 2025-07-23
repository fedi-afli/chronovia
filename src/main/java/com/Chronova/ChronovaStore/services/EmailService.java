package com.Chronova.ChronovaStore.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String toEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Email Verification - Chronovia Store");
        message.setText(buildVerificationEmailContent(token));

        mailSender.send(message);
    }

    private String buildVerificationEmailContent(String token) {
        return "Welcome to Chronovia Store!\n\n" +
                "Please verify your email address by clicking the link below:\n" +
                "http://localhost:8080/api/auth/verify-email-redirect?token=" + token + "\n\n" +
                "This link will expire in 24 hours.\n\n" +
                "If you didn't create an account with us, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Chronovia Store Team";
    }

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Password Reset - Chronovia Store");
        message.setText(buildPasswordResetEmailContent(resetToken));

        mailSender.send(message);
    }

    private String buildPasswordResetEmailContent(String resetToken) {
        return "Password Reset Request\n\n" +
                "You have requested to reset your password. Click the link below to reset it:\n" +
                "http://localhost:8080/api/auth/reset-password?token=" + resetToken + "\n\n" +
                "This link will expire in 1 hour.\n\n" +
                "If you didn't request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Chronovia Store Team";
    }

    public void sendHtmlEmail(String toEmail, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true indicates HTML

        mailSender.send(message);
    }





}