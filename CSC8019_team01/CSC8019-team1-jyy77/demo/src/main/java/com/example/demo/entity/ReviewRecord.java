package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing a book review submitted by a user.
 * Stores the review's rating, comment, related user and book IDs, and timestamp.
 *
 * Author: Guanyuan Wang
 */
@Entity
@Table(name = "review_records")
public class ReviewRecord {

    /**
     * Primary key for the review record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewRecordId;

    /**
     * ID of the user who submitted the review.
     */
    @Column(nullable = false)
    private int userId;

    /**
     * ID of the book being reviewed.
     */
    @Column(nullable = false)
    private int bookId;

    /**
     * Rating given by the user (e.g. 1–5).
     */
    @Column(nullable = false)
    private Integer rating;

    /**
     * Optional comment provided by the user.
     */
    private String comment;

    /**
     * Timestamp when the review was created.
     * Defaults to the current time.
     */
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters

    public int getReviewRecordId() {
        return reviewRecordId;
    }

    public void setReviewRecordId(int reviewRecordId) {
        this.reviewRecordId = reviewRecordId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
