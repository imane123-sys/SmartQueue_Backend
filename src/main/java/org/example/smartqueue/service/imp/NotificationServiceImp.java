package org.example.smartqueue.service.imp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.response.NotificationResponseDTO;
import org.example.smartqueue.entity.*;
import org.example.smartqueue.enums.StatutNotification;
import org.example.smartqueue.enums.StatutTicket;
import org.example.smartqueue.mapper.NotificationMapper;
import org.example.smartqueue.repository.ClientRepository;
import org.example.smartqueue.repository.NotificationRepository;
import org.example.smartqueue.repository.TicketRepository;
import org.example.smartqueue.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
@Service
@RequiredArgsConstructor
public  class NotificationServiceImp implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender mailSender;
    private final TicketRepository ticketRepository;
    private final ClientRepository clientRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendEmailAsync(String to, String subject, String body){
        CompletableFuture.runAsync(() -> {
            try {
                SimpleMailMessage email = new SimpleMailMessage();
                email.setFrom(fromEmail);
                email.setTo(to);
                email.setSubject(subject);
                email.setText(body);
                mailSender.send(email);
                System.out.println("Email envoyé avec succès à " + to);
            } catch(Exception e) {
                System.err.println("Erreur lors de l'envoi de l'email à " + to + " : " + e.getMessage());
            }
        });
    }

    @Override
    @Transactional
    public void notificationConfirmation(Ticket ticket){
        String titre = "Ticket créé";
        String message = "Votre ticket a été créé.";
        saveNotification(ticket, titre, message, org.example.smartqueue.enums.Role.CLIENT);
        sendEmailAsync(ticket.getClient().getEmail(), titre, message);
    }

    @Override
    @Transactional
    public void notificationConfirmation(long idTicket) {
        Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("ce ticket n'existe pas "));
        notificationConfirmation(ticket);
    }

    @Override
    @Transactional
    public void notificationUrTurn(long idTicket, long idClient, Ticket ticket){
        Ticket t = (ticket != null) ? ticket : ticketRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("ce ticket n'existe pas "));
        saveNotification(
                t,
                "C'est votre tour",
                "C'est votre tour.",
                org.example.smartqueue.enums.Role.CLIENT
        );
    }

    @Override
    @Transactional
    public void sendTurnApproachingNotification(long idTicket, long position, long tempsEstime) {
        Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("ce ticket n'existe pas "));
        saveNotification(
                ticket,
                "Tour approche",
                "Votre tour approche.",
                org.example.smartqueue.enums.Role.CLIENT
        );
    }

    @Override
    @Transactional
    public void notificationAnnulationTicket(long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ce ticket n'existe pas "));

        ticket.setStatut(StatutTicket.ABSENT);
        ticketRepository.save(ticket);

        saveNotification(
                ticket,
                "Ticket annulé",
                "Un ticket a été annulé.",
                org.example.smartqueue.enums.Role.ETABLISSEMENT
        );
    }

    @Override
    @Transactional
    public Notification saveNotification(Ticket ticket, String titre, String message) {
        return saveNotification(ticket, titre, message, org.example.smartqueue.enums.Role.CLIENT);
    }

    @Override
    @Transactional
    public Notification saveNotification(Ticket ticket, String titre, String message, org.example.smartqueue.enums.Role destinataireRole) {
        Notification notification = new Notification();
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setDateEnvoi(LocalDateTime.now());
        notification.setStatut(StatutNotification.ENVOYE);
        notification.setTicket(ticket);
        notification.setDestinataireRole(destinataireRole);

        Notification saved = notificationRepository.save(notification);

        Object payload = (notificationMapper != null) ? notificationMapper.toResponseDTO(saved) : saved;

        try {
            if (destinataireRole == org.example.smartqueue.enums.Role.CLIENT) {
                Long clientId = ticket.getClient().getId();
                messagingTemplate.convertAndSend("/topic/notifications/" + clientId, payload);
            } else if (destinataireRole == org.example.smartqueue.enums.Role.ETABLISSEMENT) {
                Long etablissementId = ticket.getServices().getEtablissement().getId();
                messagingTemplate.convertAndSend("/topic/notifications/etablissement/" + etablissementId, payload);
            }
        } catch (Exception e) {
            System.err.println("Erreur WebSocket: " + e.getMessage());
        }

        return saved;
    }
    @Override
    public Page <NotificationResponseDTO> getNotificationsTicketsEtablissement(long idEtablissement, Pageable pageable){
        Page<Notification> notifications =notificationRepository.findByTicket_Services_Etablissement_Id(idEtablissement,pageable);
        return notifications.map(notificationMapper::toResponseDTO);

    }
    @Override
    public   Page<NotificationResponseDTO>getNotificationsByClient(long idClient,Pageable pageable)
    {
        Page<Notification> notifications =notificationRepository.findByTicketClientId(idClient,pageable);
        return notifications.map(notificationMapper::toResponseDTO);

    }







}
