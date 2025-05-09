package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
/**

 * Class Name: EmailService

 *

 * Purpose:

 * Provides email-sending functionality for system notifications.

 * Sends plain-text confirmation emails during user registration, borrowing, and returning books.

 *

 * Interface Description:

 * - sendSimpleMessage: Core method to send plain-text email.

 * - sendRegistrationConfirmation: Sends registration success email.

 * - sendBorrowConfirmation: Sends confirmation email after a book is borrowed.

 * - sendReturnConfirmation: Sends confirmation email after a book is returned.

 *

 * Important Data:

 * - emailSender: Spring Boot's JavaMailSender used to send emails.

 *

 * Development History:

 * - Designed by: Yunyi Jiang

 * - Reviewed by: Peilin Li

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-02 (Initial version)

 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    // send email
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("jiangyunyi000@gmail.com");
        emailSender.send(message);
    }


    public void sendRegistrationConfirmation(String userEmail) {
        String subject = "Registration Confirmation of Teams01's Ebook library";
        String text = "Thank you for registering Team01’s EBook System! Your account has been created successfully.";
        sendSimpleMessage(userEmail, subject, text);
    }

    public void sendBorrowConfirmation(String userEmail, String bookTitle) {
        String subject = "Borrow Confirmation";
        String text = "You have successfully borrowed the book: " + bookTitle + ".";
        sendSimpleMessage(userEmail, subject, text);
    }

    public void sendReturnConfirmation(String userEmail, String bookTitle) {
        String subject = "Return Confirmation";
        String text = "You have successfully returned the book: " + bookTitle + ". Thank you!";
        sendSimpleMessage(userEmail, subject, text);
    }
}
