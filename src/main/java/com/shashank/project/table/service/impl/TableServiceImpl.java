package com.shashank.project.table.service.impl;

import com.shashank.project.db.jooq.tables.pojos.Table;
import com.shashank.project.table.dto.request.TableCreateRequestDTO;
import com.shashank.project.table.dto.request.TableUpdateRequestDTO;
import com.shashank.project.table.dto.response.TableCreateResponseDTO;
import com.shashank.project.table.dto.response.TableGetResponseDTO;
import com.shashank.project.table.dto.response.TableUpdateResponseDTO;
import com.shashank.project.table.mapper.TableMapper;
import com.shashank.project.table.repository.TableRepository;
import com.shashank.project.table.service.TableIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TableServiceImpl implements TableIService {

    private final TableMapper tableMapper;
    private final TableRepository tableRepository;

    @Override
    @Transactional
    public TableCreateResponseDTO createTable(TableCreateRequestDTO request) {

        UUID id = UUID.randomUUID();
        Table table = tableMapper.toEntity(request, id);
        tableRepository.save(table);

        return tableMapper.toCreateResponse(table, "Table Created Successfully");
    }

    @Override
    public TableGetResponseDTO getById(UUID id) {

        Table table = tableRepository.getById(id);

        return tableMapper.toGetResponse(table);
    }

    @Override
    public TableUpdateResponseDTO update(UUID id, TableUpdateRequestDTO request) {

        Table tableExisting = tableRepository.getById(id);

        if(request.getMobileNumber() != null) {
            tableExisting.setMobileNumber(request.getMobileNumber());
        }

        if(request.getEmail() != null) {
            tableExisting.setEmail(request.getEmail());
        }

        tableRepository.update(tableExisting);

        return tableMapper.toUpdateEntity(tableExisting, "Table Updated Successfully!");
    }
}
