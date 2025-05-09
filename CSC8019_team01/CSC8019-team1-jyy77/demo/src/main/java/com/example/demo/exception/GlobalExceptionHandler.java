package com.example.demo.exception;

import com.example.demo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
/**

 * Class Name: GlobalExceptionHandler

 *

 * Purpose:

 * A global exception handler for intercepting and managing application-wide errors in a consistent JSON format.

 * It converts system and business-level exceptions into standardized response structures using the Result wrapper.

 *

 * Interface Description:

 * - Handles NoSuchElementException for 404 "not found" cases.

 * - Handles RuntimeException for known business logic issues.

 * - Handles all uncaught exceptions and returns a 500 internal error.

 *

 * Development History:

 * - Designed by: Peilin Li

 * - Reviewed by: Yunyi Jiang Guanyuan Wang

 * - Created on: 2025-04-29

 * - Last modified: 2025-05-02 (Initial version)

 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Data not found (book or user does not exist)
    @ExceptionHandler(NoSuchElementException.class)
    public Result<String> handleNotFound(NoSuchElementException e) {
        return Result.fail(404, e.getMessage());
    }

    // General business logic exception (e.g., duplicate registration)
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException e) {
        return Result.fail(400, e.getMessage());
    }

    // Unknown system exception
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace();  //
        return Result.fail(500, "nternal server error: " + e.getMessage());
    }
}
