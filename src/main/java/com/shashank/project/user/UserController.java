package com.shashank.project.user;

import com.shashank.project.common.ApiResponse;
import com.shashank.project.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    @PostMapping("/all")
    @Operation(summary = "Get all users", description = "Retrieve all users with pagination and optional filtering")
    public ResponseEntity<ApiResponse<PaginatedResponse<UserDTO>>> getAllUsers(
            @RequestBody(required = false) PaginationRequest request) {

        if (request == null) {
            request = PaginationRequest.builder().build();
        }

        PaginatedResponse<UserDTO> response = userService.getAllUsers(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their UUID")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(
            @Parameter(description = "User UUID") @PathVariable UUID id) {

        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieve a specific user by their email address")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(
            @Parameter(description = "User email") @PathVariable String email) {

        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Create a new user")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        UserDTO createdUser = userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created successfully", createdUser));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @Parameter(description = "User UUID") @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request) {

        UserDTO updatedUser = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", updatedUser));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by ID")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "User UUID") @PathVariable UUID id) {

        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate user", description = "Activate a deactivated user")
    public ResponseEntity<ApiResponse<UserDTO>> activateUser(
            @Parameter(description = "User UUID") @PathVariable UUID id) {

        UserDTO user = userService.activateUser(id);
        return ResponseEntity.ok(ApiResponse.success("User activated successfully", user));
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate user", description = "Deactivate an active user")
    public ResponseEntity<ApiResponse<UserDTO>> deactivateUser(
            @Parameter(description = "User UUID") @PathVariable UUID id) {

        UserDTO user = userService.deactivateUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deactivated successfully", user));
    }
}

