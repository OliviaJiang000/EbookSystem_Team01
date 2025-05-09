package com.example.demo.repository;

import com.example.demo.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for managing Wishlist entities.
 * Provides access to wishlist data based on user ID.
 *
 * Author: Guanyuan Wang
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    /**
     * Retrieves the wishlist associated with a specific user.
     *
     * @param userId the ID of the user
     * @return an Optional containing the wishlist if found, otherwise empty
     */
    Optional<Wishlist> findByUserId(int userId);
}
