package com.work.pinkweb.Candidate.Service;

public interface EmailService {

    void sendEmail(String to, String subject, String text);
}
