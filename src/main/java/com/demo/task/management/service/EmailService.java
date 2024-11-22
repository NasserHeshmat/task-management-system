package com.demo.task.management.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
