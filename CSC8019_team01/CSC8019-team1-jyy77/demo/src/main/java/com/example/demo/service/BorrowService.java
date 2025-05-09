package com.example.demo.service;

import com.example.demo.entity.BorrowRecord;
import java.util.List;

/**
 * Service interface for managing borrowing operations.
 * Handles borrowing, returning, and retrieving borrowing history of books.
 *
 * Author: Guanyuan Wang
 */
public interface BorrowService {

    /**
     * Borrows a book for the given user.
     *
     * @param userId the ID of the user borrowing the book
     * @param bookId the ID of the book to be borrowed
     */
    void borrowBook(int userId, int bookId);

    /**
     * Returns a borrowed book for the given user.
     *
     * @param userId the ID of the user returning the book
     * @param bookId the ID of the book to be returned
     */
    void returnBook(int userId, int bookId);

    /**
     * Retrieves the borrowing history for a given user.
     *
     * @param userId the ID of the user
     * @return list of borrow records
     */
    List<BorrowRecord> getBorrowHistory(int userId);

    /**
     * Retrieves all borrow records in the system.
     *
     * @return list of all borrow records
     */
    List<BorrowRecord> getAllBorrowRecords();
}
