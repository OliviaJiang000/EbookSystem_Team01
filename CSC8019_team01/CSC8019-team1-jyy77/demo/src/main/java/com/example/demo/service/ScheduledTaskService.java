package com.example.demo.service;

import com.example.demo.config.TaskConfig;
import com.example.demo.entity.BorrowRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
/**

 * Class Name: ScheduledTaskService

 *

 * Purpose:

 * Defines scheduled tasks for the EBook system, such as sending automatic return reminders to users.

 * It checks borrowed books daily and notifies users whose due dates are approaching.

 *

 * Interface Description:

 * - sendReturnReminder(): Executes daily at 9:00 AM. Retrieves all borrow records,

 *   checks for due dates, and sends emails to users in advance.

 *

 * Important Data:

 * - EmailService: sends email notifications.

 * - BorrowService: retrieves all borrow records from the database.

 * - TaskConfig: provides configurable reminder offset (e.g., days before due date).

 *

 * Development History:

 * - Designed by: Yunyi Jiang

 * - Reviewed by: Peilin Li

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-02 (Initial version)

 */
@Service
public class ScheduledTaskService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private TaskConfig taskConfig;  //  TaskConfig

    // check every day if date is due date
    @Scheduled(cron = "0 0 9 * * ?") // excute every 9 am
    public void sendReturnReminder() {
        List<BorrowRecord> borrowRecords = borrowService.getAllBorrowRecords();
        int daysBeforeDueDate = taskConfig.getDueDateReminderDays();  // set dates  before due date

        for (BorrowRecord record : borrowRecords) {
            LocalDate dueDate = record.getDueDate();
            if (dueDate != null && dueDate.isBefore(LocalDate.now().plusDays(daysBeforeDueDate))) {
                String userEmail = record.getUser().getEmail();
                String bookTitle = record.getBook().getTitle();
                String subject = "Return Reminder: Book Due Soon";
                String text = "This is a reminder that the book '" + bookTitle + "' is due for return in " + daysBeforeDueDate + " days.";
                emailService.sendSimpleMessage(userEmail, subject, text);
            }
        }
    }
}
