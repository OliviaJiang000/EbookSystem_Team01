package com.example.demo.controller;


import com.example.demo.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-email")
public class EmailControllerTest {

    private final EmailService emailService;

    public EmailControllerTest(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public String testSendEmail() {
        emailService.sendEmail(
                "y.jiang65@newcastle.ac.uk",   // 比如 your-email@gmail.com
                "测试邮件",
                "你好，这是一个测试邮件！"
        );
        return "邮件已发送";
    }
}
