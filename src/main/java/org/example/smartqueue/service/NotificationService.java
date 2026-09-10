package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.TicketRequestDTO;
import org.example.smartqueue.dto.response.NotificationResponseDTO;
import org.example.smartqueue.entity.Notification;
import org.example.smartqueue.entity.Ticket;

import java.util.List;

public interface NotificationService {
    void notificationConfirmation(Ticket ticket);
    void sendTurnApproachingNotification(long idTicket,long position ,long tempsEstime );
    void notificationUrTurn(long idTicket,long idClient,Ticket ticket);
    void notificationAnnulationTicket(long id);
    List<NotificationResponseDTO>getNotificationsByClient(long idClient);
    void sendEmailAsync(String to, String subject, String body);
     Notification saveNotification(Ticket ticket, String titre, String message);

}
