package com.example.demo.controller;

import com.example.demo.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false) // ➔ 不用认证直接测
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService; // 因为Controller依赖Service，我们用Mock假的

    @Test
    public void testGetAllGenres() throws Exception {
        // 假设 service 返回了 ["Fiction", "Science", "History"]
        given(bookService.findDistinctGenres())
                .willReturn(Arrays.asList("Fiction", "Science", "History"));

        mockMvc.perform(get("/books/genres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0]").value("Fiction"))
                .andExpect(jsonPath("$[1]").value("Science"))
                .andExpect(jsonPath("$[2]").value("History"));
    }
}
