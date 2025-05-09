package com.example.demo.controller;

import com.example.demo.entity.BorrowRecord;
import com.example.demo.service.BorrowService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing book borrowing and returning.
 * The controller uses JWT tokens from the Authorization header to identify users.
 *
 * Author: Guanyuan Wang
 */
@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private UserService userService;

    /**
     * Allows an authenticated user to borrow a book.
     * The user is identified by the JWT token in the Authorization header.
     *
     * @param authHeader the Authorization header containing the JWT token (format: "Bearer <token>")
     * @param bookId the ID of the book to be borrowed
     * @return confirmation message
     *
     * Author: Guanyuan Wang
     */
    @PostMapping("/borrow")
    public String borrowBook(@RequestHeader("Authorization") String authHeader,
                             @RequestParam int bookId) {
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        int userId = userService.getUserIdFromToken(token);
        borrowService.borrowBook(userId, bookId);
        return "Book borrowed successfully.";
    }

    /**
     * Allows an authenticated user to return a borrowed book.
     * The user is identified via the JWT token in the Authorization header.
     *
     * @param authHeader the Authorization header containing the JWT token (format: "Bearer <token>")
     * @param bookId the ID of the book to be returned
     * @return confirmation message
     *
     * Author: Guanyuan Wang
     */
    @PostMapping("/return")
    public String returnBook(@RequestHeader("Authorization") String authHeader,
                             @RequestParam int bookId) {
        String token = authHeader.substring(7);
        int userId = userService.getUserIdFromToken(token);
        borrowService.returnBook(userId, bookId);
        return "Book returned successfully.";
    }

    /**
     * Retrieves the borrowing history of the authenticated user.
     * The user is identified by their JWT token.
     *
     * @param authHeader the Authorization header containing the JWT token (format: "Bearer <token>")
     * @return list of borrow records for the user
     *
     * Author: Guanyuan Wang
     */
    @GetMapping("/history")
    public List<BorrowRecord> getBorrowHistory(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        int userId = userService.getUserIdFromToken(token);
        return borrowService.getBorrowHistory(userId);
    }
}
