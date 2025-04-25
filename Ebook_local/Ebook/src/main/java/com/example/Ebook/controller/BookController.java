package com.example.Ebook.controller;

import com.example.Ebook.entity.Book;
import com.example.Ebook.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return bookService.getAllBooks(page, size);
    }
        // 根据 ID 获取单本书籍
        @GetMapping("/{id}")
        public Book getBookById(@PathVariable Integer id) {
            return bookService.getBookById(id);
        }

}
