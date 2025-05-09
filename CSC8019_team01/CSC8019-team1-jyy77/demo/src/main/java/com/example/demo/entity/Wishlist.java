package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class representing a user's wishlist.
 * Each wishlist is associated with one user and contains a list of wishlist items (books).
 *
 * Author: Guanyuan Wang
 */
@Entity
@Table(name = "wishlists")
public class Wishlist {

    /**
     * Primary key for the wishlist.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishlistId;

    /**
     * ID of the user who owns the wishlist.
     */
    @Column(nullable = false)
    private int userId;

    /**
     * Timestamp when the wishlist was created.
     * Automatically set to the current time.
     */
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * List of books (wishlist items) in the wishlist.
     * One-to-many relationship with WishlistItem.
     * Items are deleted if the wishlist is removed.
     */
    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishlistItem> items;

    // Getters and Setters

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<WishlistItem> getItems() {
        return items;
    }

    public void setItems(List<WishlistItem> items) {
        this.items = items;
    }
}
