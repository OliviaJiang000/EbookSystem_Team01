package com.example.demo.dto;
/**

 * Class Name: UpdateUserRequest

 *

 * Purpose:

 * DTO used for updating user profile information, such as nickname, email,

 * and password. The request requires the current password when changing to a new one.

 *

 * Interface Description:

 * - Contains optional fields for nickname and email update.

 * - Requires both currentPassword and newPassword to update credentials.

 *

 * Important Data:

 * - nickname: optional user-friendly display name.

 * - email: updated email address (must be valid and unique).

 * - currentPassword: must match existing password to authorize changes.

 * - newPassword: the new password to apply.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Guanyuan Wang

 * - Created on: 2025-04-27

 * - Last modified: 2025-05-01 (Initial version)

 */
public class UpdateUserRequest {
    private String nickname;
    private String email;
    private String currentPassword;
    private String newPassword;

    // Getter and Setter
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
