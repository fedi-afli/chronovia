package com.Chronova.ChronovaStore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        message.setSubject("Email Verification - Chronova Store");
        message.setText(buildVerificationEmailContent(token));
        
        mailSender.send(message);
    }

    private String buildVerificationEmailContent(String token) {
        return "Welcome to Chronova Store!\n\n" +
               "Please verify your email address by clicking the link below:\n" +
               "http://localhost:8080/api/auth/verify-email?token=" + token + "\n\n" +
               "This link will expire in 24 hours.\n\n" +
               "If you didn't create an account with us, please ignore this email.\n\n" +
               "Best regards,\n" +
               "Chronova Store Team";
    }

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Password Reset - Chronova Store");
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
               "Chronova Store Team";
    }
}