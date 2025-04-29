package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

public class BorrowService {

    @Autowired
    private EmailService emailService;

    //当用户借阅书籍时，发送相应的确认邮件
    public void sendBorrowConfirmation(String userEmail, String bookTitle) {
        String subject = "Borrow Confirmation";
        String text = "You have successfully borrowed the book: " + bookTitle + ".";
        emailService.sendEmail(userEmail, subject, text);
    }

    //当用户归还书籍时，发送相应的确认邮件
    public void sendReturnConfirmation(String userEmail, String bookTitle) {
        String subject = "Return Confirmation";
        String text = "You have successfully returned the book: " + bookTitle + ". Thank you!";
        emailService.sendEmail(userEmail, subject, text);
    }
}
