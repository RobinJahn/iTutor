package com.example.itutor.service.impl;

import com.example.itutor.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmail(String toEmail) {
        if (isValidEmail(toEmail)) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(toEmail);
            mailMessage.setSubject("Registration Confirmation");
            mailMessage.setText("You have successfully registered.");
            mailSender.send(mailMessage);
        } else {
            // Hier könntest du Logik hinzufügen, wenn die E-Mail ungültig ist
            System.out.println("Ungültige E-Mail-Adresse: " + toEmail);
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        // Regulärer Ausdruck für einfache E-Mail-Validierung
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
