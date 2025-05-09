package com.example.demo;

import com.example.demo.controller.AuthController;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthController authController;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Test
    void contextLoads() {
        
    }

    @Test
    void userServiceShouldBeInjected() {
        assertThat(userService).isNotNull();
    }

    @Test
    void bookServiceShouldBeInjected() {
        assertThat(bookService).isNotNull();
    }

    @Test
    void authControllerShouldBeInjected() {
        assertThat(authController).isNotNull();
    }

    @Test
    void datasourceUrlShouldBeSet() {
        assertThat(dbUrl).contains("jdbc:mysql");
    }
}
