package org.example.smartqueue.mapper;


import org.example.smartqueue.dto.request.TicketRequestDTO;
import org.example.smartqueue.dto.response.TicketResponseDTO;
import org.example.smartqueue.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.nom", target = "nomClient")
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "service.nom", target = "nomService")
    @Mapping(source = "service.etablissement.nom", target = "nomEtablissement")
    TicketResponseDTO toResponseDTO(Ticket ticket);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "numero", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "tempsEstime", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "service", ignore = true)
    Ticket toEntity(TicketRequestDTO requestDTO);
}