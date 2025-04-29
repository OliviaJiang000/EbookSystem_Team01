package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
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

    // 新增接口：获取所有种类
    @GetMapping("/genres")
    public List<String> getAllGenres() {
        return bookService.findDistinctGenres();
    }

    // 根据 genre 获取书籍
    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }

    // 新增接口：搜索书籍（根据书名or作者名）
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam("keyword") String keyword) {
        return bookService.searchBooks(keyword);
    }


}
