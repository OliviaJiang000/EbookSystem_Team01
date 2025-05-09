package com.example.demo.dto;

import com.example.demo.entity.Book;
import com.example.demo.entity.ReviewRecord;

import java.util.List;
/**

 * Class Name: BookDetailResponse

 *

 * Purpose:

 * Data Transfer Object (DTO) that represents a detailed view of a book,

 * including its basic information and associated user reviews.

 * This class is typically used for frontend display of a book detail page.

 *

 * Interface Description:

 * - Contains a Book object and a list of ReviewRecord objects.

 * - Provides constructor, getters, and setters for both fields.

 *

 * Important Data:

 * - book: the book being queried

 * - reviews: the list of reviews associated with the book

 *

 * Development History:

 * - Designed by:Yunyi Jiang

 * - Reviewed by: Guanyuan Wang

 * - Created on: 2025-04-28

 * - Last modified: 2025-05-02 (Initial version)

 */
public class BookDetailResponse {
    private Book book;
    private List<ReviewRecord> reviews;

    public BookDetailResponse(Book book, List<ReviewRecord> reviews) {
        this.book = book;
        this.reviews = reviews;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<ReviewRecord> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewRecord> reviews) {
        this.reviews = reviews;
    }
}
