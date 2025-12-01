package com.shashank.project.table.controller;

import com.shashank.project.table.dto.request.TableCreateRequestDTO;
import com.shashank.project.table.dto.request.TableUpdateRequestDTO;
import com.shashank.project.table.dto.response.TableCreateResponseDTO;
import com.shashank.project.table.dto.response.TableGetResponseDTO;
import com.shashank.project.table.dto.response.TableUpdateResponseDTO;
import com.shashank.project.table.service.TableIService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/table")
@AllArgsConstructor
@Builder
public class TableController {

    private final TableIService tableIService;

    @PostMapping("/create")
    public ResponseEntity<TableCreateResponseDTO> createTable(
            @Valid @RequestBody TableCreateRequestDTO request) {
        TableCreateResponseDTO response = tableIService.createTable(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableGetResponseDTO> getById(@PathVariable UUID id) {
        TableGetResponseDTO response = tableIService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TableUpdateResponseDTO> updatedTable(@PathVariable UUID id, @RequestBody @Valid TableUpdateRequestDTO request) {
        TableUpdateResponseDTO response = tableIService.update(id, request);
        return ResponseEntity.ok(response);
    }


}
