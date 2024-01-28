package com.example.itutor.service;

public interface EmailSenderService {

    void sendEmail(String toEmail);

    void sendEmailWithContent(String toEmail, String subject, String text);
}
