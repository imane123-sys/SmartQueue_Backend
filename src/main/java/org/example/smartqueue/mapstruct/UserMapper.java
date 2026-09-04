package org.example.smartqueue.mapstruct;

import org.example.smartqueue.dto.request.RegisterRequestDTO;
import org.example.smartqueue.dto.response.ClientResponseDTO;
import org.example.smartqueue.dto.response.UserResponseDTO;
import org.example.smartqueue.entity.Client;
import org.example.smartqueue.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toUserResponseDTO(User user);

    ClientResponseDTO toClientResponseDTO(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    Client toClientEntity(RegisterRequestDTO registerRequestDTO);
}