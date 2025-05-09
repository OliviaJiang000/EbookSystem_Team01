
package com.example.demo.service.impl;

import com.example.demo.dto.WishlistBookDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Wishlist;
import com.example.demo.entity.WishlistItem;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.WishlistItemRepository;
import com.example.demo.repository.WishlistRepository;
import com.example.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the WishlistService interface.
 * Handles logic for adding, removing, and retrieving books in a user's wishlist.
 *
 * Author: Guanyuan Wang
 */
@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Adds a book to the user's wishlist.
     * If the wishlist doesn't exist for the user, it will be created.
     * Prevents duplicate entries.
     *
     * @param userId the ID of the user
     * @param bookId the ID of the book to add
     */
    @Override
    public void addBookToWishlist(Integer userId, Integer bookId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Wishlist newWishlist = new Wishlist();
                    newWishlist.setUserId(userId);
                    newWishlist.setCreatedAt(LocalDateTime.now());
                    return wishlistRepository.save(newWishlist);
                });

        boolean exists = wishlistItemRepository.existsByWishlist_WishlistIdAndBookId(wishlist.getWishlistId(), bookId);
        if (exists) {
            throw new RuntimeException("This book is already in your wishlist.");
        }

        WishlistItem item = new WishlistItem();
        item.setWishlist(wishlist);
        item.setBookId(bookId);
        wishlistItemRepository.save(item);
    }

    /**
     * Removes a book from the user's wishlist.
     * If the book is not found in the wishlist, no action is taken.
     *
     * @param userId the ID of the user
     * @param bookId the ID of the book to remove
     */
    @Override
    public void removeBookFromWishlist(Integer userId, Integer bookId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found for user: " + userId));

        List<WishlistItem> items = wishlistItemRepository.findByWishlistWishlistId(wishlist.getWishlistId());
        for (WishlistItem item : items) {
            if (item.getBookId() == bookId) {
                wishlistItemRepository.delete(item);
                break;
            }
        }
    }

    /**
     * Retrieves a list of books currently in the user's wishlist.
     * Converts WishlistItem entities to DTOs for presentation.
     *
     * @param userId the ID of the user
     * @return list of WishlistBookDto containing book details
     */
    @Override
    public List<WishlistBookDto> getWishlistBooks(Integer userId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found for user: " + userId));

        List<WishlistItem> items = wishlistItemRepository.findByWishlistWishlistId(wishlist.getWishlistId());
        List<WishlistBookDto> result = new ArrayList<>();

        for (WishlistItem item : items) {
            Book book = bookRepository.findById(item.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found: " + item.getBookId()));
            result.add(new WishlistBookDto(
                    book.getBookId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCoverImage()
            ));
        }

        return result;
    }
}
