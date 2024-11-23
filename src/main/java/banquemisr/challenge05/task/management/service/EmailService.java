package banquemisr.challenge05.task.management.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
