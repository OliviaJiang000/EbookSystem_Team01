package com.example.demo.service.impl;

import com.example.demo.entity.ReviewRecord;
import com.example.demo.repository.ReviewRecordRepository;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the ReviewService interface.
 * Handles logic for submitting and retrieving book reviews.
 *
 * Author: Guanyuan Wang
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRecordRepository reviewRecordRepository;

    /**
     * Adds a new review for a book by a specific user.
     * The review includes a rating and an optional comment.
     *
     * @param userId  the ID of the user submitting the review
     * @param bookId  the ID of the book being reviewed
     * @param rating  the rating score (e.g. 1–5)
     * @param comment optional text content of the review
     */
    @Override
    public void addReview(Integer userId, Integer bookId, Integer rating, String comment) {
        ReviewRecord review = new ReviewRecord();
        review.setUserId(userId);
        review.setBookId(bookId);
        review.setRating(rating);
        review.setComment(comment);
        reviewRecordRepository.save(review);
    }

    /**
     * Retrieves all reviews associated with a given book ID.
     *
     * @param bookId the ID of the book
     * @return list of review records
     */
    @Override
    public List<ReviewRecord> getReviewsByBook(Integer bookId) {
        return reviewRecordRepository.findByBookId(bookId);
    }
}
