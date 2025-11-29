package com.shashank.project.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Response DTO for user status change (activate/deactivate).
 *
 * @author Shashank
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusResponseDTO {
    private UUID id;
    private String status;
    private String message;
    private Boolean success;
}

