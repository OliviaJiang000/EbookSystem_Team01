package com.example.demo.repository;

import com.example.demo.entity.ReviewRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for managing ReviewRecord entities.
 * Provides methods to retrieve review data from the database.
 *
 * Author: Guanyuan Wang
 */
public interface ReviewRecordRepository extends JpaRepository<ReviewRecord, Integer> {

    /**
     * Finds all review records for a specific book by its ID.
     *
     * @param bookId the ID of the book
     * @return list of review records associated with the book
     */
    List<ReviewRecord> findByBookId(int bookId);
}
