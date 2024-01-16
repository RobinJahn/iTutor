package com.example.itutor.service;

public interface EmailServiceI {

    void sendConfirmationEmail(String to, String subject, String text);
}
