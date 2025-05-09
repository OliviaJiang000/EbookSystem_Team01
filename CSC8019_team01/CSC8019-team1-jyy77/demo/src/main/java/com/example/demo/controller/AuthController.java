package com.example.demo.controller;

import com.example.demo.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;

import com.example.demo.entity.User;

import com.example.demo.service.UserService;

/**

 * Class Name: AuthController

 *

 * Purpose:

 * Provides RESTful API endpoints for user authentication and profile operations,

 * including registration, login, user info retrieval, and user updates.

 * All actions are managed by the UserService layer.

 *

 * Interface Description:

 * - POST /api/auth/register: Registers a new user.

 * - POST /api/auth/login: Authenticates a user and returns a JWT token.

 * - GET /api/auth/me: Retrieves the current user's profile.

 * - PUT /api/auth/email: Updates the user's email address.

 * - PUT /api/auth/update: Updates user's name and other profile data.

 *

 * Important Data:

 * - Uses Authorization headers with Bearer tokens to identify users.

 * - Depends on DTO classes such as RegisterRequest, LoginRequest, AuthResponse, etc.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang

 * - Created on: 2025-04-27

 * - Last modified: 2025-05-01 (Initial version)

 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok("Registration successful！");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = userService.login(request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The username or password is incorrect");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMe(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        User user = userService.getCurrentUser(token);
        return ResponseEntity.ok(new UserProfileResponse(user));
    }


    @PutMapping("/email")
    public ResponseEntity<String> updateEmail(@RequestHeader("Authorization") String authHeader,
                                              @RequestBody EmailUpdateRequest request) {
        String token = authHeader.substring(7);
        userService.updateEmail(token, request.getNewEmail());
        return ResponseEntity.ok("The email address has been updated successfully!");
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody UpdateUserRequest request) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or incorrectly formatted tokens");
            }

            String token = authHeader.substring(7);
            userService.updateUserInfo(token, request);
            return ResponseEntity.ok("The information update was successful.");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The server processing failed.：" + e.getMessage());
        }
    }
}