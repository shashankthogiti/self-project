package com.shashank.project.user.service;

import com.shashank.project.user.dto.request.*;
import com.shashank.project.user.dto.response.*;

import java.util.UUID;

/**
 * Service interface for User business operations.
 * <p>
 * This interface defines the contract for user-related business logic.
 *
 * @author Shashank
 * @since 1.0.0
 */
public interface UserIService {

    /**
     * Creates a new user with the provided details.
     * <p>
     * This method performs the following operations:
     * <ul>
     *   <li>Validates that the email doesn't already exist</li>
     *   <li>Encodes the password using BCrypt</li>
     *   <li>Creates and persists the user entity</li>
     *   <li>Returns a success response with the created user ID</li>
     * </ul>
     *
     * @param request The user creation request containing all required details
     * @return UserCreateResponseDTO containing the created user ID and success message
     * @throws com.shashank.project.user.exception.UserAlreadyExistsException if email already exists
     */
    UserCreateResponseDTO createUser(UserCreateRequestDTO request);

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve
     * @return UserResponseDTO containing complete user details
     * @throws com.shashank.project.user.exception.UserNotFoundException if user doesn't exist
     */
    UserResponseDTO getUserById(UUID userId);

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email of the user to retrieve
     * @return UserResponseDTO containing complete user details
     * @throws com.shashank.project.user.exception.UserNotFoundException if user doesn't exist
     */
    UserResponseDTO getUserByEmail(String email);

    /**
     * Retrieves all users with pagination and filtering.
     *
     * @param request The request containing pagination and filter criteria
     * @return GetAllUsersResponseDTO containing paginated list of users
     */
    GetAllUsersResponseDTO getAllUsers(GetAllUsersRequestDTO request);

    /**
     * Updates an existing user with the provided details.
     *
     * @param userId  The ID of the user to update
     * @param request The user update request containing fields to update
     * @return UserUpdateResponseDTO containing the updated user ID and success message
     * @throws com.shashank.project.user.exception.UserNotFoundException if user doesn't exist
     * @throws com.shashank.project.user.exception.UserAlreadyExistsException if new email already exists
     */
    UserUpdateResponseDTO updateUser(UUID userId, UserUpdateRequestDTO request);

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete
     * @return UserDeleteResponseDTO containing the deleted user ID and success message
     * @throws com.shashank.project.user.exception.UserNotFoundException if user doesn't exist
     */
    UserDeleteResponseDTO deleteUser(UUID userId);

    /**
     * Activates a user by setting their isActive flag to true.
     *
     * @param userId The ID of the user to activate
     * @return UserStatusResponseDTO containing the user status and success message
     * @throws com.shashank.project.user.exception.UserNotFoundException if user doesn't exist
     */
    UserStatusResponseDTO activateUser(UUID userId);

    /**
     * Deactivates a user by setting their isActive flag to false.
     *
     * @param userId The ID of the user to deactivate
     * @return UserStatusResponseDTO containing the user status and success message
     * @throws com.shashank.project.user.exception.UserNotFoundException if user doesn't exist
     */
    UserStatusResponseDTO deactivateUser(UUID userId);
}

