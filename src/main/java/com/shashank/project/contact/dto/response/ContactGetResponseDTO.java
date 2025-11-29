package com.shashank.project.contact.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactGetResponseDTO {

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
