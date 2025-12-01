package com.shashank.project.table.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableUpdateResponseDTO {

    private UUID id;
    private String message;
    private Boolean isActive;

}
