package com.example.demo.ControllerTest;

import com.example.demo.controller.BorrowController;
import com.example.demo.entity.BorrowRecord;
import com.example.demo.service.BorrowService;
import com.example.demo.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BorrowController.class)
public class BorrowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowService borrowService;

    @MockBean
    private UserService userService;

    private final String token = "Bearer test.jwt.token";

    @Test
    public void testBorrowBook() throws Exception {
        Mockito.when(userService.getUserIdFromToken(Mockito.anyString())).thenReturn(1);

        mockMvc.perform(post("/borrow/borrow")
                        .header("Authorization", token)
                        .param("bookId", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book borrowed successfully."));

        Mockito.verify(borrowService).borrowBook(1, 10);
    }

    @Test
    public void testReturnBook() throws Exception {
        Mockito.when(userService.getUserIdFromToken(Mockito.anyString())).thenReturn(1);

        mockMvc.perform(post("/borrow/return")
                        .header("Authorization", token)
                        .param("bookId", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book returned successfully."));

        Mockito.verify(borrowService).returnBook(1, 10);
    }

    @Test
    public void testGetBorrowHistory() throws Exception {
        Mockito.when(userService.getUserIdFromToken(Mockito.anyString())).thenReturn(1);
        Mockito.when(borrowService.getBorrowHistory(1)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/borrow/history")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }
}
