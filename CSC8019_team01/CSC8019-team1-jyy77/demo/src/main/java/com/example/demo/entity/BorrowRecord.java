package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entity class representing a borrow record for a book loan.
 * Each record is linked to one user and one book, and stores the borrow and due dates.
 *
 * Author: Guanyuan Wang
 */
@Entity
@Table(name = "borrow_records")
public class BorrowRecord {

    /**
     * The primary key for the borrow record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int borrowRecordId;

    /**
     * The user who borrowed the book.
     * Many borrow records can be associated with one user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The book that was borrowed.
     * Many borrow records can be associated with one book.
     */
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    /**
     * The date when the book was borrowed.
     */
    @Column(nullable = false, name = "borrow_date")
    private LocalDate borrowDate;

    /**
     * The due date by which the book should be returned.
     */
    @Column(nullable = false, name = "due_date")
    private LocalDate dueDate;

    /**
     * Flag indicating whether the book has been returned.
     */
    @Column(nullable = false, name = "is_returned")
    private Boolean isReturned = false;

    // Getters and Setters

    public int getBorrowRecordId() {
        return borrowRecordId;
    }

    public void setBorrowRecordId(int borrowRecordId) {
        this.borrowRecordId = borrowRecordId;
    }

    public User getUser() {
        return user;
    }

    /**
     * Gets the ID of the associated user.
     * @return user ID
     */
    public Long getUserId() {
        return user.getId();
    }

    /**
     * Sets the user ID by creating or updating the associated User object.
     * @param userId the user ID
     */
    public void setUserId(Long userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(userId);
    }

    public Book getBook() {
        return book;
    }

    /**
     * Gets the ID of the associated book.
     * @return book ID
     */
    public int getBookId() {
        return book.getBookId();
    }

    /**
     * Sets the book ID by creating or updating the associated Book object.
     * @param bookId the book ID
     */
    public void setBookId(int bookId) {
        if (this.book == null) {
            this.book = new Book();
        }
        this.book.setBookId(bookId);
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(Boolean isReturned) {
        this.isReturned = isReturned;
    }
}
