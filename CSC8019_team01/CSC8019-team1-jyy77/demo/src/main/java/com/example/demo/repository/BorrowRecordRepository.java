package com.example.demo.repository;

import com.example.demo.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for accessing borrow record data from the database.
 * Extends JpaRepository to provide basic CRUD operations and custom queries for borrow history.
 *
 * Author: Yunyi Jiang,Guanyuan Wang
 */
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Integer> {

    /**
     * Finds all active (not yet returned) borrow records for a given user.
     *
     * @param userId the ID of the user
     * @return list of borrow records where isReturned = false
     */
    List<BorrowRecord> findByUser_IdAndIsReturnedFalse(int userId);

    /**
     * Finds all borrow records (returned and not returned) for a given user.
     *
     * @param userId the ID of the user
     * @return list of all borrow records associated with the user
     */
    List<BorrowRecord> findByUser_Id(int userId);

    /**
     * Checks if a user has already borrowed a specific book and has not returned it yet.
     *
     * @param userId the ID of the user
     * @param bookId the ID of the book
     * @return true if an active borrow record exists, false otherwise
     */
    boolean existsByUser_IdAndBook_BookIdAndIsReturnedFalse(int userId, int bookId);
}
