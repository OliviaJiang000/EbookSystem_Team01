package com.example.demo.dto;

import com.example.demo.entity.User;
/**

 * Class Name: UserProfileResponse

 *

 * Purpose:

 * DTO used for exposing limited user information to the client, typically in profile views or admin dashboards.

 * It is constructed from a full User entity, but only contains safe and relevant fields.

 *

 * Interface Description:

 * - Read-only structure (no setters), with four fields: id, username, email, role.

 * - Constructed from a User entity, and throws exception if user role is null.

 *

 * Important Data:

 * - id: user's unique database identifier.

 * - username: login or display name.

 * - email: registered email address.

 * - role: user's access level (e.g., USER, ADMIN).

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Guanyuan Wang

 * - Created on: 2025-04-28

 * - Last modified: 2025-05-01 (Initial version)

 */
public class UserProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String role;

    public UserProfileResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        if (user.getRole() == null) {
            throw new IllegalStateException("user have not setting yet");
        }
        this.role = user.getRole().name();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}

