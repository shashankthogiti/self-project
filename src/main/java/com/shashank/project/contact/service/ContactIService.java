package com.shashank.project.contact.service;

import com.shashank.project.contact.dto.request.ContactCreateRequestDTO;
import com.shashank.project.contact.dto.request.ContactUpdateRequestDTO;
import com.shashank.project.contact.dto.response.ContactCreateResponseDTO;
import com.shashank.project.contact.dto.response.ContactGetResponseDTO;
import com.shashank.project.contact.dto.response.ContactUpdateResponseDTO;

import java.util.UUID;

public interface ContactIService {

    /**
     * Creates a new contact.
     *
     * @param request the contact creation request
     * @return the created contact response
     */
    ContactCreateResponseDTO createContact(ContactCreateRequestDTO request);

    ContactGetResponseDTO getById(UUID id);

    ContactUpdateResponseDTO updateContact(ContactUpdateRequestDTO request, UUID id);

}
