package com.shashank.project.user.service.impl;

import com.shashank.project.db.jooq.tables.pojos.User;
import com.shashank.project.user.dto.request.*;
import com.shashank.project.user.dto.response.*;
import com.shashank.project.user.exception.UserAlreadyExistsException;
import com.shashank.project.user.exception.UserNotFoundException;
import com.shashank.project.user.mapper.UserMapper;
import com.shashank.project.user.repository.UserRepository;
import com.shashank.project.user.service.UserIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of UserIService interface.
 * <p>
 * This service handles user-related business logic.
 *
 * @author Shashank
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserIService {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserCreateResponseDTO createUser(UserCreateRequestDTO request) {
        log.info("Creating new user with email: {}", request.getEmail());

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("User already exists with email: {}", request.getEmail());
            throw new UserAlreadyExistsException(request.getEmail());
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Create user entity
        UUID userId = UUID.randomUUID();
        User user = userMapper.toEntity(request, userId, encodedPassword);

        // Save user
        userRepository.save(user);

        log.info("User created successfully with ID: {}", userId);
        return userMapper.toCreateResponse(user, "User created successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(UUID userId) {
        log.info("Fetching user by ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public GetAllUsersResponseDTO getAllUsers(GetAllUsersRequestDTO request) {
        applyDefaults(request);

        log.info("Fetching users with page: {}, pageSize: {}", request.getPage(), request.getPageSize());

        List<User> users = userRepository.findAll(request);
        int totalCount = userRepository.count(request);

        List<UserResponseDTO> userDTOs = users.stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());

        log.info("Fetched {} users, total count: {}", users.size(), totalCount);
        return GetAllUsersResponseDTO.of(userDTOs, totalCount, request.getPage(), request.getPageSize());
    }

    @Override
    @Transactional
    public UserUpdateResponseDTO updateUser(UUID userId, UserUpdateRequestDTO request) {
        log.info("Updating user with ID: {}", userId);

        // Fetch existing user
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Check if email is being changed and if new email already exists
        if (request.getEmail() != null && !request.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                log.error("Email already exists: {}", request.getEmail());
                throw new UserAlreadyExistsException(request.getEmail());
            }
        }

        // Update fields
        if (request.getName() != null) {
            existingUser.setName(request.getName());
        }
        if (request.getEmail() != null) {
            existingUser.setEmail(request.getEmail());
        }
        if (request.getMobileNumber() != null) {
            existingUser.setMobileNumber(request.getMobileNumber());
        }
        if (request.getIsActive() != null) {
            existingUser.setIsActive(request.getIsActive());
        }
        existingUser.setUpdatedOn(OffsetDateTime.now());

        // Save updated user
        userRepository.update(existingUser);

        log.info("User updated successfully with ID: {}", userId);
        return userMapper.toUpdateResponse(existingUser, "User updated successfully");
    }

    @Override
    @Transactional
    public UserDeleteResponseDTO deleteUser(UUID userId) {
        log.info("Deleting user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        userRepository.deleteById(userId);

        log.info("User deleted successfully with ID: {}", userId);
        return userMapper.toDeleteResponse(user, "User deleted successfully");
    }

    @Override
    @Transactional
    public UserStatusResponseDTO activateUser(UUID userId) {
        log.info("Activating user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setIsActive(true);
        user.setUpdatedOn(OffsetDateTime.now());
        userRepository.update(user);

        log.info("User activated successfully with ID: {}", userId);
        return userMapper.toStatusResponse(user, "ACTIVE", "User activated successfully");
    }

    @Override
    @Transactional
    public UserStatusResponseDTO deactivateUser(UUID userId) {
        log.info("Deactivating user with ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        user.setIsActive(false);
        user.setUpdatedOn(OffsetDateTime.now());
        userRepository.update(user);

        log.info("User deactivated successfully with ID: {}", userId);
        return userMapper.toStatusResponse(user, "INACTIVE", "User deactivated successfully");
    }

    private void applyDefaults(GetAllUsersRequestDTO request) {
        if (request.getPage() == null || request.getPage() < 1) {
            request.setPage(DEFAULT_PAGE);
        }
        if (request.getPageSize() == null || request.getPageSize() < 1) {
            request.setPageSize(DEFAULT_PAGE_SIZE);
        }
    }
}

