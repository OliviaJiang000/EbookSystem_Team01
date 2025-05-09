package com.example.demo.repository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**

 * Interface Name: BookRepository

 *

 * Purpose:

 * Repository interface for Book entity. Provides data access operations for books,

 * including custom queries for genre listing and keyword-based search.

 *

 * Interface Description:

 * - Extends JpaRepository for standard CRUD operations on Book entities.

 * - Includes custom query methods for genre filtering and text search.

 *

 * Development History:

 * - Designed by: Yunyi Jiang

 * - Reviewed by: Guanyuan Wang

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-02 (Initial version)

 */
public interface BookRepository extends JpaRepository<Book, Integer> {
    // Add Method 1: Find all distinct categories.
    @Query("SELECT DISTINCT b.genre FROM Book b WHERE b.genre IS NOT NULL")
    List<String> findDistinctGenres();

    // Add Method 2: Find books by category.
    List<Book> findByGenre(String genre);

    // Add Method 3:Search for books with keywords in the title or author.
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:keyword% OR b.author LIKE %:keyword%")
    List<Book> searchByTitleOrAuthor(@Param("keyword") String keyword);

}
