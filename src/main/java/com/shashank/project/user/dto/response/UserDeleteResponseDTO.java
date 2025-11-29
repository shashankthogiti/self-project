package com.shashank.project.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Response DTO for user deletion.
 *
 * @author Shashank
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeleteResponseDTO {
    private UUID id;
    private String message;
    private Boolean success;
}

