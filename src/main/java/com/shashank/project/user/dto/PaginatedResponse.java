package com.shashank.project.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO for paginated results.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> data;
    private Integer totalCount;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;

    public static <T> PaginatedResponse<T> of(List<T> data, Integer totalCount, Integer page, Integer pageSize) {
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        return PaginatedResponse.<T>builder()
                .data(data)
                .totalCount(totalCount)
                .page(page)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .build();
    }
}

