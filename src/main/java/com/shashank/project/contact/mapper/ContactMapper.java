package com.shashank.project.contact.mapper;

import java.util.UUID;

import com.shashank.project.contact.dto.response.ContactGetResponseDTO;
import com.shashank.project.contact.dto.response.ContactUpdateResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.shashank.project.contact.dto.request.ContactCreateRequestDTO;
import com.shashank.project.contact.dto.response.ContactCreateResponseDTO;
import com.shashank.project.db.jooq.tables.pojos.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "mobileNumber", source = "request.mobileNumber")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdOn", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Contact toEntity(ContactCreateRequestDTO request, UUID id);

    @Mapping(target = "id", source = "contact.id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "success", constant = "true")
    ContactCreateResponseDTO toResponse(Contact contact, String message);

    ContactGetResponseDTO toResponseGet(Contact contact);

    @Mapping(target = "id", source = "contact.id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "isActive", source = "contact.isActive")
    ContactUpdateResponseDTO toResponseUpdate(Contact contact, String message);
}
