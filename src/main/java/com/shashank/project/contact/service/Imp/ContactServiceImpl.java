package com.shashank.project.contact.service.Imp;

import com.shashank.project.contact.dto.request.ContactCreateRequestDTO;
import com.shashank.project.contact.dto.request.ContactUpdateRequestDTO;
import com.shashank.project.contact.dto.response.ContactCreateResponseDTO;
import com.shashank.project.contact.dto.response.ContactGetResponseDTO;
import com.shashank.project.contact.dto.response.ContactUpdateResponseDTO;
import com.shashank.project.contact.mapper.ContactMapper;
import com.shashank.project.contact.repository.ContactRepository;
import com.shashank.project.contact.service.ContactIService;
import com.shashank.project.db.jooq.tables.pojos.Contact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactIService {

    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;

    @Transactional
    @Override
    public ContactCreateResponseDTO createContact(ContactCreateRequestDTO request) {

        UUID id = UUID.randomUUID();
        Contact contact = contactMapper.toEntity(request, id);

        contactRepository.save(contact);

        return contactMapper.toResponse(contact, "Contact Added Successfully");

    }

    @Transactional
    @Override
    public ContactGetResponseDTO getById(UUID id) {
        Contact contact = contactRepository.findById(id);

        return contactMapper.toResponseGet(contact);
    }

    @Transactional
    @Override
    public ContactUpdateResponseDTO updateContact(ContactUpdateRequestDTO request, UUID id) {

        Contact contactExisting = contactRepository.findById(id);

        if(request.getName() != null) {
            contactExisting.setName(request.getName());
        }

        if(request.getMobileNumber() != null) {
            contactExisting.setMobileNumber(request.getMobileNumber());
        }

        if(request.getEmail() != null) {
            contactExisting.setEmail(request.getEmail());
        }

        contactRepository.update(contactExisting);

        return contactMapper.toResponseUpdate(contactExisting, "Contact Updated!");

    }

}
