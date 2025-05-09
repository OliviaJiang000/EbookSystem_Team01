package com.example.demo.service;

import com.example.demo.entity.ReviewRecord;
import java.util.List;

/**
 * Service interface for managing book review operations.
 * Allows users to add reviews and retrieve reviews by book ID.
 *
 * Author: Guanyuan Wang
 */
public interface ReviewService {

    /**
     * Adds a review for a specific book by a user.
     *
     * @param userId  the ID of the user submitting the review
     * @param bookId  the ID of the book being reviewed
     * @param rating  the rating score (e.g., 1–5)
     * @param comment optional comment content for the review
     */
    void addReview(Integer userId, Integer bookId, Integer rating, String comment);

    /**
     * Retrieves all reviews associated with a specific book.
     *
     * @param bookId the ID of the book
     * @return a list of ReviewRecord objects for the given book
     */
    List<ReviewRecord> getReviewsByBook(Integer bookId);
}
