package com.example.Ebook.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    private String title;
    private String author;
    private String genre;
    @Column(name = "cover_image")
    private String coverImage;
    private String description;
    @Column(name = "available_copies")
    private Integer leftCopies;
    @Column(name = "loan_duration")
    private Integer loanDuration;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters & Setters
    public Integer getBookId() {
        return bookId;
    }


}
