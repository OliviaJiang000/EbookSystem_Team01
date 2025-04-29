package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
//归还提醒邮件:借阅的书籍即将到期（例如，借阅到期前 3 天），发送归还提醒邮件。
public class ScheduledTaskService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private BorrowService borrowService; // 假设 BorrowService 提供了获取所有借阅书籍的方法

    // 每天定时检查借阅到期的书籍，并发送提醒邮件
    @Scheduled(cron = "0 0 9 * * ?") // 每天 9 点执行
    public void sendReturnReminder() {
        List<BorrowRecord> borrowRecords = borrowService.getAllBorrowRecords();

        for (BorrowRecord record : borrowRecords) {
            LocalDate dueDate = record.getDueDate();
            if (dueDate != null && dueDate.isBefore(LocalDate.now().plusDays(3))) {
                String userEmail = record.getUser().getEmail();
                String bookTitle = record.getBook().getTitle();
                String subject = "Return Reminder: Book Due Soon";
                String text = "This is a reminder that the book '" + bookTitle + "' is due for return in 3 days.";
                emailService.sendEmail(userEmail, subject, text);
            }
        }
    }
}
