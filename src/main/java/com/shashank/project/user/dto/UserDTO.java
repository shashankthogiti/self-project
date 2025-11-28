package com.shashank.project.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for User entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String mobileNumber;
    private Boolean isActive;
    private OffsetDateTime createdOn;
    private OffsetDateTime updatedOn;
    private UUID createdBy;
    private UUID updatedBy;
}

