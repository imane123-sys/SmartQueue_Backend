package org.example.smartqueue.mapper;


import org.example.smartqueue.dto.request.TicketRequestDTO;
import org.example.smartqueue.dto.response.TicketResponseDTO;
import org.example.smartqueue.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.nom", target = "nomClient")
    @Mapping(source = "services.id", target = "serviceId")
    @Mapping(source = "services.nom", target = "nomService")
    @Mapping(source = "services.etablissement.nom", target = "nomEtablissement")
    TicketResponseDTO toResponseDTO(Ticket ticket);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numero", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "tempsEstime", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "services", ignore = true)
    Ticket toEntity(TicketRequestDTO requestDTO);
    List<TicketResponseDTO> toDTOList(List<Ticket>tickets);
}