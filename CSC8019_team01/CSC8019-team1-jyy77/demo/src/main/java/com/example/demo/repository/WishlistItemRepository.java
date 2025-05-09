package com.example.demo.repository;

import com.example.demo.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for managing WishlistItem entities.
 * Provides methods to query wishlist items by wishlist ID and book ID.
 *
 * Author: Guanyuan Wang
 */
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {

    /**
     * Retrieves all wishlist items belonging to a specific wishlist.
     *
     * @param wishlistId the ID of the wishlist
     * @return list of wishlist items
     */
    List<WishlistItem> findByWishlistWishlistId(int wishlistId);

    /**
     * Checks whether a specific book already exists in the given wishlist.
     *
     * @param wishlistId the ID of the wishlist
     * @param bookId the ID of the book
     * @return true if the item exists, false otherwise
     */
    boolean existsByWishlist_WishlistIdAndBookId(int wishlistId, int bookId);
}
