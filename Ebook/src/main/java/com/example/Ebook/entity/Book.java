package com.example.Ebook.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    private String title;
    private String author;
    private String genre;
    private String coverImage;
    private String description;
    private int leftCopies;
    private int loanDuration;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters & Setters
}
