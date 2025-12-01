package com.shashank.project.table.service;

import com.shashank.project.db.jooq.tables.pojos.Contact;
import com.shashank.project.db.jooq.tables.pojos.Table;
import com.shashank.project.table.dto.request.TableCreateRequestDTO;
import com.shashank.project.table.dto.request.TableUpdateRequestDTO;
import com.shashank.project.table.dto.response.TableCreateResponseDTO;
import com.shashank.project.table.dto.response.TableGetResponseDTO;
import com.shashank.project.table.dto.response.TableUpdateResponseDTO;

import java.util.UUID;

public interface TableIService {

    TableCreateResponseDTO createTable(TableCreateRequestDTO request);

    TableGetResponseDTO getById(UUID id);

    TableUpdateResponseDTO update(UUID id, TableUpdateRequestDTO request);

}
