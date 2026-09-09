package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.TicketRequestDTO;
import org.example.smartqueue.dto.response.NotificationResponseDTO;
import org.example.smartqueue.entity.Notification;
import org.example.smartqueue.entity.Ticket;

import java.util.List;

public interface NotificationService {
    void notificationConfirmation(Ticket ticket);
    void sendTurnApproachingNotification(TicketRequestDTO ticket, int positionRestante);
    void notificationUrTurn(TicketRequestDTO ticket);
    void notificationAnnulationTicket(long id);
    List<NotificationResponseDTO>getNotificationsByClient(long idClient);
    void sendEmailAsync(String to, String subject, String body);
     Notification saveNotification(Ticket ticket, String titre, String message);

}
