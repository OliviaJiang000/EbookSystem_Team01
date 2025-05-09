package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
/**

 * Class Name: TaskConfig

 *

 * Purpose:

 * This configuration class provides access to application-level settings related to scheduled tasks,

 * specifically the number of days in advance to trigger due date reminders.

 * The value is typically read from the application's properties file.

 *

 * Interface Description:

 * - Provides a getter method to retrieve the number of days before a task's due date that a reminder should be sent.

 * - Uses @Value to inject a configurable value from application properties with a default fallback.

 *

 * Important Data:

 * - dueDateReminderDays: number of days before due date when reminders should be triggered; defaults to 3.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-01 (Initial version)

 */
@Configuration
public class TaskConfig {

    // use @Value to read from config
    @Value("${task.dueDateReminderDays:3}")  // default 3
    private int dueDateReminderDays;

    public int getDueDateReminderDays() {
        return dueDateReminderDays;
    }
}
