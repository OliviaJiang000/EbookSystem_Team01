package com.example.demo.dto;

/**
 * Data Transfer Object for submitting a book review.
 * Contains the book ID, rating, and an optional comment from the user.
 *
 * Author: Guanyuan Wang
 */
public class ReviewRequest {
    private Integer bookId;    // The ID of the book being reviewed
    private Integer rating;    // The rating score given by the user (e.g. 1–5)
    private String comment;    // The textual comment for the review (optional)

    /**
     * Gets the ID of the book being reviewed.
     * @return bookId
     */
    public Integer getBookId() {
        return bookId;
    }

    /**
     * Sets the ID of the book being reviewed.
     * @param bookId the book ID
     */
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    /**
     * Gets the rating score.
     * @return rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * Sets the rating score.
     * @param rating the score (typically 1 to 5)
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * Gets the user's review comment.
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the user's review comment.
     * @param comment the review text
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
