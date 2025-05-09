package com.example.demo.service;

import com.example.demo.entity.WishlistItem;
import java.util.List;
import com.example.demo.dto.WishlistBookDto;

/**
 * Service interface for managing wishlist operations.
 * Supports adding, removing, and retrieving books in a user's wishlist.
 *
 * Author: Guanyuan Wang
 */
public interface WishlistService {

    /**
     * Adds a book to the specified user's wishlist.
     *
     * @param userId the ID of the user
     * @param bookId the ID of the book to add
     */
    void addBookToWishlist(Integer userId, Integer bookId);

    /**
     * Removes a book from the specified user's wishlist.
     *
     * @param userId the ID of the user
     * @param bookId the ID of the book to remove
     */
    void removeBookFromWishlist(Integer userId, Integer bookId);

    /**
     * Retrieves the list of books in the user's wishlist.
     *
     * @param userId the ID of the user
     * @return list of books in the wishlist represented as WishlistBookDto objects
     */
    List<WishlistBookDto> getWishlistBooks(Integer userId);
}
