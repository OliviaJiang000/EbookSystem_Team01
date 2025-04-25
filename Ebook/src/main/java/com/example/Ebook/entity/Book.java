package com.example.Ebook.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
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
    private int leftCopies;
    @Column(name = "loan_duration")
    private int loanDuration;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters & Setters
    public Integer getBookId() {
        return bookId;
    }


}
