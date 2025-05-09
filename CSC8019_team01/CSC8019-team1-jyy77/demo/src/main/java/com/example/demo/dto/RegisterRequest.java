package com.example.demo.dto;
/**

 * Class Name: RegisterRequest

 *

 * Purpose:

 * DTO used for handling user registration requests.

 * Encapsulates user input including username, password, and email.

 * This object is typically received from the frontend during signup.

 *

 * Interface Description:

 * - Provides fields for username, password, and email.

 * - Includes standard getter and setter methods for Spring data binding.

 *

 * Important Data:

 * - username: must be unique across users.

 * - password: must be hashed before storage.

 * - email: must follow email format and also be unique.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Guanyuan Wang

 * - Created on: 2025-04-27

 * - Last modified: 2025-05-01 (Initial version)

 */
public class RegisterRequest {
    private String username;
    private String password;
    private String email;

    // Getter and Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
