package com.shashank.project.contact.controller;

import com.shashank.project.contact.dto.request.ContactCreateRequestDTO;
import com.shashank.project.contact.dto.request.ContactUpdateRequestDTO;
import com.shashank.project.contact.dto.response.ContactCreateResponseDTO;
import com.shashank.project.contact.dto.response.ContactGetResponseDTO;
import com.shashank.project.contact.dto.response.ContactUpdateResponseDTO;
import com.shashank.project.contact.service.ContactIService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Builder
public class ContactController {

    private final ContactIService contactIService;

    @PostMapping("/create")
    public ResponseEntity<ContactCreateResponseDTO> createContact(
            @Valid @RequestBody ContactCreateRequestDTO request) {
        ContactCreateResponseDTO response = contactIService.createContact(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactGetResponseDTO> getById(@PathVariable UUID id) {
        ContactGetResponseDTO response = contactIService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ContactUpdateResponseDTO> update(@PathVariable UUID id, @RequestBody ContactUpdateRequestDTO request) {
        ContactUpdateResponseDTO response = contactIService.updateContact(request, id);

        return ResponseEntity.ok(response);
    }

}
