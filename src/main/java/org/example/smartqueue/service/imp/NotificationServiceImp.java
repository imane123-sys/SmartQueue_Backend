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
        String titre = "Confirmation de ticket";
        String nomService = ticket.getServices() != null ? ticket.getServices().getNom() : "";
        String nomEtablissement = (ticket.getServices() != null && ticket.getServices().getEtablissement() != null)
                ? ticket.getServices().getEtablissement().getNom() : "";

        String message = String.format("Votre ticket N° %s a été créé pour le service %s chez %s.",
                ticket.getNumero(), nomService, nomEtablissement);
        saveNotification(ticket,titre,message);
        sendEmailAsync(ticket.getClient().getEmail(),titre,message);

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
        Ticket tickets = ticketRepository.findById(idTicket).orElseThrow(()->new RuntimeException("ce ticket n'existe pas "));
        saveNotification(
                tickets,
                "C'est votre tour !",
                "Votre ticket est maintenant appelé. Veuillez vous présenter à l'établissement."
        );

    }
    @Override
    @Transactional
    public void sendTurnApproachingNotification(long idTicket, long position, long tempsEstime) {
        Ticket ticket = ticketRepository.findById(idTicket)
                .orElseThrow(() -> new RuntimeException("ce ticket n'existe pas "));
        saveNotification(
                ticket,
                "Votre tour s'approche !",
                "Il reste environ " + position +
                        " personnes avant votre tour. Votre tour est prévu dans environ " + tempsEstime + " minutes."
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
                "Annulation de ticket",
                "Le ticket N° " + ticket.getNumero() + " a été annulé."
        );
    }

    @Transactional
    public Notification saveNotification(Ticket ticket, String titre, String message) {

        Notification notification = new Notification();
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setDateEnvoi(LocalDateTime.now());
        notification.setStatut(StatutNotification.ENVOYE);
        notification.setTicket(ticket);
        Notification saved = notificationRepository.save(notification);

        Object payload = (notificationMapper != null) ? notificationMapper.toResponseDTO(saved) : saved;

        if (messagingTemplate != null) {
            try {
                if (ticket != null && ticket.getClient() != null && ticket.getClient().getId() != null) {
                    long idClient = ticket.getClient().getId();
                    messagingTemplate.convertAndSend(
                            "/topic/notifications/" + idClient,
                            payload
                    );
                }

                if (ticket != null && ticket.getServices() != null && ticket.getServices().getEtablissement() != null && ticket.getServices().getEtablissement().getId() != null) {
                    long idEtablissement = ticket.getServices().getEtablissement().getId();
                    messagingTemplate.convertAndSend(
                            "/topic/notifications/etablissement/" + idEtablissement,
                            payload
                    );
                }
            } catch (Exception e) {
                System.err.println("Erreur envoi WebSocket notification: " + e.getMessage());
            }
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
