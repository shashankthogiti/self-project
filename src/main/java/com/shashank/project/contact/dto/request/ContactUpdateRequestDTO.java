package com.shashank.project.contact.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactUpdateRequestDTO {

    private String name;
    private String email;
    private String mobileNumber;
    private boolean isActive;

}
