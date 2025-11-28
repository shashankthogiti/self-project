package com.shashank.project.user;

import com.shashank.project.common.EntityNotFoundException;
import com.shashank.project.common.ValidationException;
import com.shashank.project.user.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service layer for User operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Get all users with pagination and filtering.
     */
    public PaginatedResponse<UserDTO> getAllUsers(PaginationRequest request) {
        applyDefaults(request);

        List<UserDTO> users = userRepository.findAll(request);
        Integer totalCount = userRepository.count(request);

        log.info("Fetched {} users, total count: {}", users.size(), totalCount);
        return PaginatedResponse.of(users, totalCount, request.getPage(), request.getPageSize());
    }

    /**
     * Get a user by ID.
     */
    public UserDTO getUserById(UUID id) {
        log.info("Fetching user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    /**
     * Get a user by email.
     */
    public UserDTO getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User", "email", email));
    }

    /**
     * Create a new user.
     */
    public UserDTO createUser(CreateUserRequest request) {
        log.info("Creating new user with email: {}", request.getEmail());

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ValidationException("Email already exists: " + request.getEmail());
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserDTO createdUser = userRepository.insert(
                request.getName(),
                request.getEmail(),
                encodedPassword,
                request.getMobileNumber()
        );

        log.info("User created successfully with id: {}", createdUser.getId());
        return createdUser;
    }

    /**
     * Update an existing user.
     */
    public UserDTO updateUser(UUID id, UpdateUserRequest request) {
        log.info("Updating user with id: {}", id);

        // Check if user exists
        UserDTO existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        // Check if email is being changed and if new email already exists
        if (request.getEmail() != null && !request.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new ValidationException("Email already exists: " + request.getEmail());
            }
        }

        // Use existing values if not provided in request
        String name = request.getName() != null ? request.getName() : existingUser.getName();
        String email = request.getEmail() != null ? request.getEmail() : existingUser.getEmail();
        String mobileNumber = request.getMobileNumber() != null ? request.getMobileNumber() : existingUser.getMobileNumber();
        Boolean isActive = request.getIsActive() != null ? request.getIsActive() : existingUser.getIsActive();

        UserDTO updatedUser = userRepository.update(id, name, email, mobileNumber, isActive);

        log.info("User updated successfully with id: {}", id);
        return updatedUser;
    }

    /**
     * Delete a user by ID.
     */
    public void deleteUser(UUID id) {
        log.info("Deleting user with id: {}", id);

        // Check if user exists
        if (userRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("User", id);
        }

        userRepository.deleteById(id);
        log.info("User deleted successfully with id: {}", id);
    }

    /**
     * Activate a user.
     */
    public UserDTO activateUser(UUID id) {
        log.info("Activating user with id: {}", id);
        UserDTO user = getUserById(id);
        return userRepository.update(id, user.getName(), user.getEmail(), user.getMobileNumber(), true);
    }

    /**
     * Deactivate a user.
     */
    public UserDTO deactivateUser(UUID id) {
        log.info("Deactivating user with id: {}", id);
        UserDTO user = getUserById(id);
        return userRepository.update(id, user.getName(), user.getEmail(), user.getMobileNumber(), false);
    }

    private void applyDefaults(PaginationRequest request) {
        if (request.getPage() == null || request.getPage() < 1) {
            request.setPage(DEFAULT_PAGE);
        }
        if (request.getPageSize() == null || request.getPageSize() < 1) {
            request.setPageSize(DEFAULT_PAGE_SIZE);
        }
    }
}

