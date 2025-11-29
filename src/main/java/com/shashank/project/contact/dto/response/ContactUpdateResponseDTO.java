package com.shashank.project.contact.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactUpdateResponseDTO {

    private UUID id;
    private String message;
    private Boolean isActive;

}
