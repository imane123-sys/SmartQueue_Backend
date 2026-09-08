package org.example.smartqueue.mapper;

import org.example.smartqueue.dto.request.ClientRequestDTO;
import org.example.smartqueue.dto.request.RegisterRequestDTO;
import org.example.smartqueue.dto.response.ClientResponseDTO;
import org.example.smartqueue.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    Client toEntity(ClientRequestDTO dto);

    ClientResponseDTO toDto(Client client);
    List<ClientResponseDTO>toDTOList(List<Client> clients);
    void updateEntityFromDTO(ClientRequestDTO clientRequestDTO, @MappingTarget Client client);
}

