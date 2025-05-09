package com.example.demo.entity;

import jakarta.persistence.*;

/**
 * Entity class representing a single item (book) in a user's wishlist.
 * Each item belongs to a wishlist and references a specific book by its ID.
 *
 * Author: Guanyuan Wang
 */
@Entity
@Table(name = "wishlist_items")
public class WishlistItem {

    /**
     * Primary key for the wishlist item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishlistItemId;

    /**
     * Reference to the parent wishlist.
     * Many items can belong to one wishlist.
     * Lazy fetching is used to avoid loading the full wishlist unless needed.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id", nullable = false)
    private Wishlist wishlist;

    /**
     * ID of the book added to the wishlist.
     */
    @Column(nullable = false)
    private int bookId;

    // Getters and Setters

    public int getWishlistItemId() {
        return wishlistItemId;
    }

    public void setWishlistItemId(int wishlistItemId) {
        this.wishlistItemId = wishlistItemId;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
