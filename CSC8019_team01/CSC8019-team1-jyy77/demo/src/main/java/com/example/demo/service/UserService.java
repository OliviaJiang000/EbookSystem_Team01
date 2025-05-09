package com.example.demo.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UpdateUserRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
/**

 * Class Name: UserService

 *

 * Purpose:

 * Handles business logic related to user registration, login, authentication,

 * JWT token decoding, profile updates, and email address management.

 *

 * Interface Description:

 * - register(): Creates a new user account with encrypted password and default role.

 * - login(): Authenticates user and issues JWT.

 * - getCurrentUser(): Extracts user from JWT token.

 * - getUserIdFromToken(): Shortcut for extracting user ID from JWT.

 * - updateEmail(): Updates user's email.

 * - updateUserInfo(): Updates user email or password with validation.

 *

 * Important Data:

 * - Uses AuthenticationManager for login validation.

 * - Uses PasswordEncoder for password hashing.

 * - Sends registration confirmation via EmailService.

 * - Token parsing and generation are handled by JwtUtil.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-03 (Initial version)

 */
@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private EmailService emailService;

    // user send email when register

    // register
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("use has been registered");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.USER); // default
        User savedUser = userRepository.save(user);
        // send email when register successfully
        emailService.sendRegistrationConfirmation(savedUser.getEmail());

    }
    public Integer getUserIdFromToken(String token) {
        return getCurrentUser(token).getId().intValue();
    }


    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("user does not exist"));

        // Check if the role is null.
        if (user.getRole() == null) {
            user.setRole(Role.USER);
            userRepository.save(user);
            throw new IllegalStateException("The user has not been assigned a role and cannot log in.");
        }

        String token = jwtUtil.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(
                                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
                        )
                )
        );

        return new AuthResponse(token);
    }


    public User getCurrentUser(String token) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User does not exist."));

        if (user.getRole() == null) {
            throw new RuntimeException("The user has not been assigned a role. Please contact the administrator");
        }

        return user;
    }


    public void updateEmail(String token, String newEmail) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEmail(newEmail);
        userRepository.save(user); // save
    }


    public void updateUserInfo(String token, UpdateUserRequest request) {
        User user = getCurrentUser(token);

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getNewPassword() != null && request.getCurrentPassword() != null) {
            if (!encoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new RuntimeException("error password");
            }
            user.setPassword(encoder.encode(request.getNewPassword()));
        }

        userRepository.save(user);
    }
}



