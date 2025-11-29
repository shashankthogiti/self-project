package com.shashank.project.user.exception;

/**
 * Exception thrown when attempting to create a user with an email that already exists.
 *
 * @author Shashank
 * @since 1.0.0
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super(String.format("User already exists with email: %s", email));
    }
}

