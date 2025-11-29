package com.shashank.project.user.exception;

import java.util.UUID;

/**
 * Exception thrown when a user is not found in the database.
 *
 * @author Shashank
 * @since 1.0.0
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID userId) {
        super(String.format("User not found with ID: %s", userId));
    }

    public UserNotFoundException(String email) {
        super(String.format("User not found with email: %s", email));
    }

    public UserNotFoundException(String field, String value) {
        super(String.format("User not found with %s: %s", field, value));
    }
}

