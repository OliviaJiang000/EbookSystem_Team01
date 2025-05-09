package com.example.demo.controller;

import com.example.demo.entity.ReviewRecord;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.ReviewRequest;

import java.util.List;

/**
 * REST controller for managing user-submitted book reviews.
 * Users are identified using the JWT token passed in the Authorization header.
 *
 * Author: Guanyuan Wang
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    /**
     * Endpoint for submitting a new book review.
     * The user is authenticated using a JWT token from the Authorization header.
     *
     * @param authHeader the Authorization header containing the JWT token (format: "Bearer <token>")
     * @param reviewRequest the review data including bookId, rating, and comment
     * @return confirmation message after review submission
     *
     * Author: Guanyuan Wang
     */
    @PostMapping("/add")
    public String addReview(@RequestHeader("Authorization") String authHeader,
                            @RequestBody ReviewRequest reviewRequest) {

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        Integer userId = userService.getUserIdFromToken(token); // Extract user ID from token

        reviewService.addReview(userId,
                reviewRequest.getBookId(),
                reviewRequest.getRating(),
                reviewRequest.getComment());

        return "Review submitted successfully.";
    }

    /**
     * Endpoint to retrieve all reviews for a specific book.
     *
     * @param bookId the ID of the book for which reviews are requested
     * @return a list of review records associated with the specified book
     *
     * Author: Guanyuan Wang
     */
    @GetMapping("/{bookId}")
    public List<ReviewRecord> getReviews(@PathVariable int bookId) {
        return reviewService.getReviewsByBook(bookId);
    }
}
