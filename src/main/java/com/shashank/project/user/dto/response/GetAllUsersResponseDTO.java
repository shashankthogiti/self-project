package com.shashank.project.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO for paginated user list.
 *
 * @author Shashank
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUsersResponseDTO {
    private List<UserResponseDTO> users;
    private PaginationMetadataDTO pagination;

    /**
     * Pagination metadata for the response.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaginationMetadataDTO {
        private Integer page;
        private Integer pageSize;
        private Integer totalCount;
        private Integer totalPages;
    }

    /**
     * Factory method to create a paginated response.
     */
    public static GetAllUsersResponseDTO of(List<UserResponseDTO> users, Integer totalCount, Integer page, Integer pageSize) {
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        return GetAllUsersResponseDTO.builder()
                .users(users)
                .pagination(PaginationMetadataDTO.builder()
                        .page(page)
                        .pageSize(pageSize)
                        .totalCount(totalCount)
                        .totalPages(totalPages)
                        .build())
                .build();
    }
}

