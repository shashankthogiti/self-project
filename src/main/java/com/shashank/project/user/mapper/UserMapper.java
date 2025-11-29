package com.shashank.project.user.mapper;

import com.shashank.project.db.jooq.tables.pojos.User;
import com.shashank.project.user.dto.request.UserCreateRequestDTO;
import com.shashank.project.user.dto.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * MapStruct mapper interface for User entity mapping.
 * <p>
 * This mapper handles conversions between DTOs and JOOQ-generated User entity.
 *
 * @author Shashank
 * @since 1.0.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps UserCreateRequestDTO to User entity.
     *
     * @param request         The user creation request DTO
     * @param id              The generated UUID for the new user
     * @param encodedPassword The BCrypt encoded password
     * @return User entity ready for persistence
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "mobileNumber", source = "request.mobileNumber")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdOn", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    User toEntity(UserCreateRequestDTO request, UUID id, String encodedPassword);

    /**
     * Maps User entity to UserResponseDTO.
     *
     * @param user The User entity
     * @return UserResponseDTO
     */
    UserResponseDTO toResponseDTO(User user);

    /**
     * Maps User entity to UserCreateResponseDTO.
     *
     * @param user    The created User entity
     * @param message Success message
     * @return UserCreateResponseDTO
     */
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "success", constant = "true")
    UserCreateResponseDTO toCreateResponse(User user, String message);

    /**
     * Maps User entity to UserUpdateResponseDTO.
     *
     * @param user    The updated User entity
     * @param message Success message
     * @return UserUpdateResponseDTO
     */
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "success", constant = "true")
    UserUpdateResponseDTO toUpdateResponse(User user, String message);

    /**
     * Maps User entity to UserDeleteResponseDTO.
     *
     * @param user    The deleted User entity
     * @param message Success message
     * @return UserDeleteResponseDTO
     */
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "success", constant = "true")
    UserDeleteResponseDTO toDeleteResponse(User user, String message);

    /**
     * Maps User entity to UserStatusResponseDTO.
     *
     * @param user    The User entity
     * @param status  The status string (ACTIVE/INACTIVE)
     * @param message Success message
     * @return UserStatusResponseDTO
     */
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "success", constant = "true")
    UserStatusResponseDTO toStatusResponse(User user, String status, String message);
}

