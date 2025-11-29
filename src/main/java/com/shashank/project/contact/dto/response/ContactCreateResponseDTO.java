package com.shashank.project.contact.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactCreateResponseDTO {

    private UUID id;
    private String message;
    private Boolean success;

}
