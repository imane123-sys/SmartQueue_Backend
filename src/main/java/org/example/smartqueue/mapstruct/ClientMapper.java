package org.example.smartqueue.mapstruct;

import org.example.smartqueue.dto.request.RegisterRequestDTO;
import org.example.smartqueue.dto.response.ClientResponseDTO;
import org.example.smartqueue.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    Client toEntity(RegisterRequestDTO dto);

    ClientResponseDTO toDto(Client entity);
}

