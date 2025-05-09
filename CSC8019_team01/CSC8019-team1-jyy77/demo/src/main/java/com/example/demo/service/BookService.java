package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**

 * Class Name: BookService

 *

 * Purpose:

 * Provides core business logic for managing books, including operations for users (query/search)

 * and administrators (add/update/delete). Supports genre filtering, keyword search, and pagination.

 *

 * Interface Description:

 * - User-accessible methods: getAllBooks, getBookById, findDistinctGenres, getBooksByGenre, searchBooks

 * - Admin-only methods: addBook, updateBook, deleteBook

 *

 * Development History:

 * - Designed by: Yunyi Jiang

 * - Reviewed by: Peilin Li

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-03 (Initial version)

 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // get all books
    public List<Book> getAllBooks(int page, int size) {
        //  PageRequest
        return bookRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    // get Book By Id
    public Book getBookById(int bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + bookId));
    }

    // get what genres we have
    public List<String> findDistinctGenres() {
        return bookRepository.findDistinctGenres();
    }

    // get Books By Genre
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    //search books by title or author
    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchByTitleOrAuthor(keyword);
    }

    // ******** admin authority ******** //

    // addBook
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // updateBook
    public Book updateBook(int bookId, Book book) {
        Optional<Book> existingBook = bookRepository.findById(bookId);
        if (existingBook.isPresent()) {
            Book updatedBook = existingBook.get();
            if (book.getTitle() != null) {
                updatedBook.setTitle(book.getTitle());
            }
            if (book.getAuthor() != null) {
                updatedBook.setAuthor(book.getAuthor());
            }
            if (book.getGenre() != null) {
                updatedBook.setGenre(book.getGenre());
            }
            if (book.getDescription() != null) {
                updatedBook.setDescription(book.getDescription());
            }
            if (book.getCoverImage() != null) {
                updatedBook.setCoverImage(book.getCoverImage());
            }
            return bookRepository.save(updatedBook);
        } else {
            return null; // if no book null
        }
    }

    // delete books
    public void deleteBook(int bookId) {
        bookRepository.deleteById(bookId);
    }


}
