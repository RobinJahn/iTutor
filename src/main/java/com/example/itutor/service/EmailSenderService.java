package com.example.itutor.service;

public interface EmailSenderService {

    void sendSignupVerificationEmail(String toEmail);

    void sendEmailWithContent(String toEmail, String subject, String text);
}
