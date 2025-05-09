package com.example.demo.ServiceTest;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    public void testGetAllBooks() {
        Book book = new Book();
        book.setTitle("Book 1");

        when(bookRepository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(Collections.singletonList(book)));

        var books = bookService.getAllBooks(0, 10);
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Book 1");
    }

    @Test
    public void testGetBookById_Success() {
        Book book = new Book();
        book.setBookId(1);
        book.setTitle("Java");

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1);
        assertThat(result.getTitle()).isEqualTo("Java");
    }

    @Test
    public void testGetBookById_NotFound() {
        when(bookRepository.findById(999)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.getBookById(999))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    public void testFindDistinctGenres() {
        when(bookRepository.findDistinctGenres()).thenReturn(Arrays.asList("Fiction", "Sci-Fi"));
        var genres = bookService.findDistinctGenres();
        assertThat(genres).contains("Fiction", "Sci-Fi");
    }

    @Test
    public void testGetBooksByGenre() {
        Book book = new Book();
        book.setGenre("Drama");

        when(bookRepository.findByGenre("Drama")).thenReturn(Collections.singletonList(book));

        var result = bookService.getBooksByGenre("Drama");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGenre()).isEqualTo("Drama");
    }

    @Test
    public void testSearchBooks() {
        Book book = new Book();
        book.setTitle("Search Book");

        when(bookRepository.searchByTitleOrAuthor("Search")).thenReturn(Collections.singletonList(book));

        var result = bookService.searchBooks("Search");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Search Book");
    }

    @Test
    public void testAddBook() {
        Book book = new Book();
        book.setTitle("New Book");

        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.addBook(book);
        assertThat(result.getTitle()).isEqualTo("New Book");
    }

    @Test
    public void testUpdateBook_Success() {
        Book existing = new Book();
        existing.setBookId(1);
        existing.setTitle("Old Title");

        Book update = new Book();
        update.setTitle("New Title");

        when(bookRepository.findById(1)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArgument(0));

        Book result = bookService.updateBook(1, update);
        assertThat(result.getTitle()).isEqualTo("New Title");
    }

    @Test
    public void testUpdateBook_NotFound() {
        when(bookRepository.findById(999)).thenReturn(Optional.empty());
        Book update = new Book();
        assertThat(bookService.updateBook(999, update)).isNull();
    }

    @Test
    public void testDeleteBook() {
        bookService.deleteBook(1);
        verify(bookRepository, times(1)).deleteById(1);
    }
}
