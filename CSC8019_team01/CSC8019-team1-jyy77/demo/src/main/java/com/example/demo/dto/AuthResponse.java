package com.example.demo.dto;
/**

 * Class Name: AuthResponse

 *

 * Purpose:

 * Represents the response returned after a successful authentication (login).

 * It encapsulates the JWT token that will be used by the client in subsequent requests.

 *

 * Interface Description:

 * - Contains a single field `token`, representing the issued JWT token.

 * - Provides standard getter and setter methods.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang

 * - Created on: 2025-04-27

 * - Last modified: 2025-05-01 (Initial version)

 */
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
