package org.example.smartqueue.mapper;


import org.example.smartqueue.dto.response.NotificationResponseDTO;
import org.example.smartqueue.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "ticket.id", target = "ticketId")
    @Mapping(source = "ticket.numero", target = "numeroTicket")
    NotificationResponseDTO toResponseDTO(Notification notification);
    List<NotificationResponseDTO>toDTOList(List<Notification>notifications);
}