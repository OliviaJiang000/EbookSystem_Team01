package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // 获取所有书籍
    public List<Book> getAllBooks(int page, int size) {
        // 如果想分页，可以用 PageRequest
        return bookRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    // 根据ID获取单本书籍
    public Book getBookById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }

    // 获取所有不同种类
    public List<String> findDistinctGenres() {
        return bookRepository.findDistinctGenres();
    }

    // 根据 genre 查找书籍
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    // 搜索书籍（根据书名or作者）
    public List<Book> searchBooks(String keyword) {
        return bookRepository.searchByTitleOrAuthor(keyword);
    }

    // ******** 管理员权限 ******** //

    // 新增书籍
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // 更新书籍
    public Book updateBook(Integer id, Book book) {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book updatedBook = existingBook.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setGenre(book.getGenre());
            updatedBook.setCoverImage(book.getCoverImage());
            updatedBook.setDescription(book.getDescription());
            updatedBook.setLeftCopies(book.getLeftCopies());
            updatedBook.setLoanDuration(book.getLoanDuration());
            return bookRepository.save(updatedBook);
        } else {
            return null; // 如果书籍未找到返回null
        }
    }

    // 删除书籍
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }


}
