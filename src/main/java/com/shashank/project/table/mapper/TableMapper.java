package com.shashank.project.table.mapper;

import com.shashank.project.db.jooq.tables.pojos.Table;
import com.shashank.project.table.dto.request.TableCreateRequestDTO;
import com.shashank.project.table.dto.request.TableUpdateRequestDTO;
import com.shashank.project.table.dto.response.TableCreateResponseDTO;
import com.shashank.project.table.dto.response.TableGetResponseDTO;
import com.shashank.project.table.dto.response.TableUpdateResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TableMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "mobileNumber", source = "request.mobileNumber")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "createdOn", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Table toEntity(TableCreateRequestDTO request, UUID id);

    @Mapping(target = "id", source = "table.id")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "success", constant = "true")
    TableCreateResponseDTO toCreateResponse(Table table, String message);

    TableGetResponseDTO toGetResponse(Table table);

    TableUpdateResponseDTO toUpdateEntity(Table table, String message);

}
