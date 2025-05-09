package com.example.demo.dto;
/**

 * Class Name: LoginRequest

 *

 * Purpose:

 * DTO representing the payload for a user login request.

 * It includes the username and password submitted by the user to obtain a JWT token.

 *

 * Interface Description:

 * - Contains two fields: username and password.

 * - Provides getter and setter methods for data binding and validation.

 *

 * Important Data:

 * - username: the user's login identity (must match existing account).

 * - password: the corresponding password (must be verified server-side).

 *

 * Development History:

 * - Designed by:Peilin Li

 * - Reviewed by: Yunyi Jiang

 * - Created on: 2025-04-28

 * - Last modified: 2025-05-01 (Initial version)

 */
public class LoginRequest {
    private String username;
    private String password;

    // generate  getter , setter method
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
}

