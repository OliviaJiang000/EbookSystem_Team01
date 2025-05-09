package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
/**

 * Class Name: AdminService

 *

 * Purpose:

 * Provides administrative operations related to user management,

 * such as deleting users and retrieving all users from the system.

 * Only accessible to users with administrative privileges.

 *

 * Interface Description:

 * - deleteUser(Long userId): Deletes a user by ID if it exists.

 * - getAllUsers(): Retrieves all users in the system.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-03 (Initial version)

 */
@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    // delete users
    @Transactional
    public void deleteUser(Long userId) {
        System.out.println("🟡 deleteUser() get param userId = " + userId);
        if (!userRepository.existsById(userId)) {
            System.out.println("🔴 user ID no exist");
            throw new NoSuchElementException("user no exist");
        }
        userRepository.deleteById(userId);
        System.out.println("✅ user has been delete: ID = " + userId);
    }


    // get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
