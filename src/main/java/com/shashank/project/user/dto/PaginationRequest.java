package com.shashank.project.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for pagination.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer pageSize = 10;

    private String nameFilter;
    private String emailFilter;
}

