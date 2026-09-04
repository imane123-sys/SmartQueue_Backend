package org.example.smartqueue.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.smartqueue.enums.StatutNotification;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationResponseDTO {
    private Long id;
    private String message;
    private LocalDateTime dateEnvoi;
    private StatutNotification statut;

    private Long ticketId;
    private int numeroTicket;
}
