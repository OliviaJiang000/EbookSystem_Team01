package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
/**

 * Class Name: Book

 *

 * Purpose:

 * Represents a book record in the library system. This entity is mapped to the 'books' table

 * in the database and holds metadata such as title, author, genre, availability, and creation time.

 *

 * Interface Description:

 * - Getters and setters are provided for each field to support CRUD operations.

 * - Designed to be used by service and repository layers when managing books.

 *

 * Important Data:

 * - bookId: Primary key, auto-incremented.

 * - leftCopies: Number of copies available for loan (default is 10).

 * - loanDuration: Maximum loan duration in days.

 * - createdAt: Timestamp for when the book record was added.

 *

 * Development History:

 * - Designed by: Yunyi Jiang

 * - Reviewed by: Peilin Li

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-01 (Initial version)

 */
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;


    private String title;
    private String author;
    private String genre;
    @Column(name = "cover_image")
    private String coverImage;
    private String description;
    @Column(name = "available_copies")
    private Integer leftCopies= 10;  // default 10
    @Column(name = "loan_duration")
    private Integer loanDuration;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // book

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLeftCopies() {
        return leftCopies;
    }

    public void setLeftCopies(Integer leftCopies) {
        this.leftCopies = leftCopies;
    }

    public Integer getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(Integer loanDuration) {
        this.loanDuration = loanDuration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
