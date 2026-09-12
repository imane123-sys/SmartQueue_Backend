package org.example.smartqueue.service;

import org.example.smartqueue.dto.response.NotificationResponseDTO;
import org.example.smartqueue.entity.Notification;
import org.example.smartqueue.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface NotificationService {
    void notificationConfirmation(Ticket ticket);
    void sendTurnApproachingNotification(long idTicket,long position ,long tempsEstime );
    void notificationUrTurn(long idTicket,long idClient,Ticket ticket);
    void notificationAnnulationTicket(long id);
    Page<NotificationResponseDTO>getNotificationsByClient(long idClient,Pageable pageable);
    Page <NotificationResponseDTO> getNotificationsTicketsEtablissement(long idEtablissement, Pageable pageable);
    void sendEmailAsync(String to, String subject, String body);
     Notification saveNotification(Ticket ticket, String titre, String message);


}
