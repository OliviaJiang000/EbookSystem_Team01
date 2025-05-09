package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**

 * Interface Name: UserRepository

 *

 * Purpose:

 * Repository interface for accessing and managing User entities.

 * Provides standard CRUD operations via JpaRepository and custom query methods

 * for login, registration, and user validation use cases.

 *

 * Interface Description:

 * - Extends JpaRepository<User, Long> to support basic persistence operations.

 * - Includes custom queries to check for duplicate usernames or emails, and to retrieve users by username.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang

 * - Created on: 2025-04-28

 * - Last modified: 2025-05-02 (Initial version)

 */
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
