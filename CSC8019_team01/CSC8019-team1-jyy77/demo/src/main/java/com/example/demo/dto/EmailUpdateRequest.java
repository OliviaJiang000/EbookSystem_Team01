package com.example.demo.dto;
/**

 * Class Name: EmailUpdateRequest

 *

 * Purpose:

 * DTO used to encapsulate the request body for updating a user's email address.

 * Typically submitted from the frontend during a profile update action.

 *

 * Interface Description:

 * - Contains one field `newEmail` representing the user's new email address.

 * - Includes standard getter and setter methods.

 *

 * Important Data:

 * - newEmail: the email address that the user wishes to update to.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang

 * - Created on: 2025-04-27

 * - Last modified: 2025-05-01 (Initial version)

 */
public class EmailUpdateRequest {
    private String newEmail;

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}

