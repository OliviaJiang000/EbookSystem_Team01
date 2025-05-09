package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.WishlistBookDto;

import java.util.List;

/**
 * REST controller for managing the user's wishlist.
 * The user is identified using a JWT token from the Authorization header.
 *
 * Author: Guanyuan Wang
 */
@RestController
@RequestMapping("/api/user/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private UserService userService;

    /**
     * Adds a book to the authenticated user's wishlist.
     *
     * @param authHeader the Authorization header containing the JWT token (format: "Bearer <token>")
     * @param bookId the ID of the book to be added to the wishlist
     * @return confirmation message
     *
     * Author: Guanyuan Wang
     */
    @PostMapping("/add")
    public String addBookToWishlist(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("bookId") Integer bookId) {

        String token = authHeader.substring(7);
        Integer userId = userService.getUserIdFromToken(token);
        wishlistService.addBookToWishlist(userId, bookId);
        return "Book added to wishlist successfully.";
    }

    /**
     * Removes a book from the authenticated user's wishlist.
     *
     * @param authHeader the Authorization header containing the JWT token (format: "Bearer <token>")
     * @param bookId the ID of the book to be removed from the wishlist
     * @return confirmation message
     *
     * Author: Guanyuan Wang
     */
    @PostMapping("/remove")
    public String removeBookFromWishlist(@RequestHeader("Authorization") String authHeader,
                                         @RequestParam Integer bookId) {
        String token = authHeader.substring(7);
        Integer userId = userService.getUserIdFromToken(token);
        wishlistService.removeBookFromWishlist(userId, bookId);
        return "Book removed from wishlist successfully.";
    }

    /**
     * Retrieves the list of books in the authenticated user's wishlist.
     *
     * @param authHeader the Authorization header containing the JWT token (format: "Bearer <token>")
     * @return a list of WishlistBookDto representing books in the user's wishlist
     *
     * Author: Guanyuan Wang
     */
    @GetMapping
    public List<WishlistBookDto> getWishlist(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer userId = userService.getUserIdFromToken(token);
        return wishlistService.getWishlistBooks(userId);
    }
}
