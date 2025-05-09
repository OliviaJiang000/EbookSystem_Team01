package com.example.demo.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
/**

 * Class Name: UserDetailsServiceImpl

 *

 * Purpose:

 * Custom implementation of Spring Security's {@link UserDetailsService} interface.

 * Loads user information from the database during authentication and provides Spring Security-compatible UserDetails.

 *

 * Interface Description:

 * - Implements the method `loadUserByUsername` used internally by Spring Security's authentication mechanism.

 * - Wraps the User entity into a Spring Security `UserDetails` object, including username, password, and authorities.

 *

 * Important Data:

 * - Injects UserRepository to query the database.

 * - Adds a single authority prefixed with "ROLE_" for compatibility with Spring's role-based access control.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-03 (Initial version)

 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not exists：" + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}

