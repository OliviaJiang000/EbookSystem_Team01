package com.example.demo.controller;

import com.example.demo.dto.BookDetailResponse;
import com.example.demo.entity.Book;
import com.example.demo.entity.ReviewRecord;
import com.example.demo.service.BookService;
import com.example.demo.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**

 * Class Name: BookController

 *

 * Purpose:

 * Exposes RESTful endpoints for querying books and book-related metadata.

 * Provides endpoints for retrieving books by ID, genre, keyword search, and associated reviews.

 * Accessible to all users (no authorization required for these endpoints).

 *

 * Interface Description:

 * - GET /books: Paginated list of books.

 * - GET /books/{bookId}: Gets a book by its ID.

 * - GET /books/{id}/details: Gets full book info and reviews.

 * - GET /books/genres: Gets all distinct book genres.

 * - GET /books/genre/{genre}: Gets books by specific genre.

 * - GET /books/search?keyword=xxx: Search by title or author.

 *

 * Important Data:

 * - BookService handles core book data retrieval.

 * - ReviewService retrieves reviews for book details.

 *

 * Development History:

 * - Designed by: Yunyi Jiang

 * - Reviewed by: Peilin Li

 * - Created on: 2025-04-28

 * - Last modified: 2025-05-02 (Initial version)

 */
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final ReviewService reviewService;

    public BookController(BookService bookService, ReviewService reviewService)  {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Book> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return bookService.getAllBooks(page, size);
    }
    // getBookById
    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable int bookId) {

        return bookService.getBookById(bookId);
    }

    @GetMapping("/{id}/details")
    public BookDetailResponse getBookDetails(@PathVariable int id) {
        Book book = bookService.getBookById(id);
        List<ReviewRecord> reviews = reviewService.getReviewsByBook(id);
        return new BookDetailResponse(book, reviews);
    }


    // add api：getAllGenres
    @GetMapping("/genres")
    public List<String> getAllGenres() {
        return bookService.findDistinctGenres();
    }

    // add api：getBooksByGenre
    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByGenre(genre);
    }

    // add api：searchBooks by book name or author
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam("keyword") String keyword) {
        return bookService.searchBooks(keyword);
    }



}
