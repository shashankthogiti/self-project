package com.shashank.project.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for getting all users with pagination and filtering.
 *
 * @author Shashank
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUsersRequestDTO {

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer pageSize = 10;

    private String nameFilter;
    private String emailFilter;

    @Builder.Default
    private SortDirection sortDirection = SortDirection.DESC;

    /**
     * Sort direction enum for ordering results.
     */
    public enum SortDirection {
        ASC, DESC
    }
}

