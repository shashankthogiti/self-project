package com.shashank.project.user.controller;

import com.shashank.project.user.dto.request.*;
import com.shashank.project.user.dto.response.*;
import com.shashank.project.user.service.UserIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for User operations.
 *
 * @author Shashank
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserIService userService;

    @PostMapping("/all")
    @Operation(summary = "Get all users", description = "Retrieve all users with pagination and optional filtering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GetAllUsersResponseDTO> getAllUsers(
            @RequestBody(required = false) GetAllUsersRequestDTO request) {
        log.info("Received request to get all users");

        if (request == null) {
            request = GetAllUsersRequestDTO.builder().build();
        }

        GetAllUsersResponseDTO response = userService.getAllUsers(request);
        log.info("Returning {} users", response.getUsers().size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "User UUID") @PathVariable UUID id) {
        log.info("Received request to get user by ID: {}", id);

        UserResponseDTO user = userService.getUserById(id);
        log.info("Returning user with ID: {}", id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieve a specific user by their email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponseDTO> getUserByEmail(
            @Parameter(description = "User email") @PathVariable String email) {
        log.info("Received request to get user by email: {}", email);

        UserResponseDTO user = userService.getUserByEmail(email);
        log.info("Returning user with email: {}", email);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserCreateResponseDTO> createUser(
            @Valid @RequestBody UserCreateRequestDTO request) {
        log.info("Received request to create user with email: {}", request.getEmail());

        UserCreateResponseDTO response = userService.createUser(request);
        log.info("User created with ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Email already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserUpdateResponseDTO> updateUser(
            @Parameter(description = "User UUID") @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequestDTO request) {
        log.info("Received request to update user with ID: {}", id);

        UserUpdateResponseDTO response = userService.updateUser(id, request);
        log.info("User updated with ID: {}", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserDeleteResponseDTO> deleteUser(
            @Parameter(description = "User UUID") @PathVariable UUID id) {
        log.info("Received request to delete user with ID: {}", id);

        UserDeleteResponseDTO response = userService.deleteUser(id);
        log.info("User deleted with ID: {}", id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate user", description = "Activate a deactivated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User activated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserStatusResponseDTO> activateUser(
            @Parameter(description = "User UUID") @PathVariable UUID id) {
        log.info("Received request to activate user with ID: {}", id);

        UserStatusResponseDTO response = userService.activateUser(id);
        log.info("User activated with ID: {}", id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate user", description = "Deactivate an active user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserStatusResponseDTO> deactivateUser(
            @Parameter(description = "User UUID") @PathVariable UUID id) {
        log.info("Received request to deactivate user with ID: {}", id);

        UserStatusResponseDTO response = userService.deactivateUser(id);
        log.info("User deactivated with ID: {}", id);
        return ResponseEntity.ok(response);
    }
}

