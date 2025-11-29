package com.shashank.project.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Response DTO for user details.
 *
 * @author Shashank
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
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

