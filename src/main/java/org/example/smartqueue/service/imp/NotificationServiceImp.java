package org.example.smartqueue.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.response.NotificationResponseDTO;
import org.example.smartqueue.entity.Client;
import org.example.smartqueue.entity.Notification;
import org.example.smartqueue.entity.Ticket;
import org.example.smartqueue.entity.User;
import org.example.smartqueue.enums.StatutNotification;
import org.example.smartqueue.mapper.NotificationMapper;
import org.example.smartqueue.repository.ClientRepository;
import org.example.smartqueue.repository.NotificationRepository;
import org.example.smartqueue.repository.TicketRepository;
import org.example.smartqueue.service.NotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public  class NotificationServiceImp implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender mailSender;
    private final TicketRepository ticketRepository;
    private final ClientRepository clientRepository;
    @Async
    public void sendEmailAsync(String to, String subject, String body){

       try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(to);
            email.setSubject(subject);
            email.setText(body);
            mailSender.send(email);
        }catch(Exception e){
           System.out.println("Erreur lors de l'envoi de l'email à"+to+e.getMessage());

       }

    }

    @Override
     public void notificationConfirmation(Ticket ticket){
        String titre = "Confirmation de ticket";
        String message =String.format("Votre ticket N° %s a été créé pour le service %s chez %s.",
                ticket.getNumero(),ticket.getServices(),ticket.getServices().getEtablissement().getNom());
        saveNotification(ticket,titre,message);
        sendEmailAsync(ticket.getClient().getEmail(),titre,message);

    }
    @Override
    public void notificationUrTurn(long idTicket, long idClient, Ticket ticket){
        Ticket tickets = ticketRepository.findById(idTicket).orElseThrow(()->new RuntimeException("ce ticket n'existe pas "));
        Notification notification = new Notification();
        notification.setTitre("C'est votre tour !");
        notification.setMessage("Votre ticket est maintenant appelé. Veuillez vous présenter au Etablissement");
        notification.setTicket(tickets);
        notificationRepository.save(notification);
    }
    @Override
     public void sendTurnApproachingNotification(long idTicket,long position ,long tempsEstime ){
        Ticket tickets = ticketRepository.findById(idTicket).orElseThrow(()->new RuntimeException("ce ticket n'existe pas "));
        Notification notification = new Notification();
        notification.setTitre("votre tour s'approche!");
        notification.setMessage("Votre ticket est maintenant appelé. Veuillez vous présenter au Etablissement");
        notification.setTicket(tickets);
        notificationRepository.save(notification);

    }

    @Override
    public void notificationAnnulationTicket(long id){

    }
    @Override
    public List<NotificationResponseDTO> getNotificationsByClient(long idClient){
        List<Notification > notifications = notificationRepository.findByTicketClientIdOrderByDateEnvoiDesc(idClient);
        return notificationMapper.toDTOList(notifications);

    }


    public Notification saveNotification(Ticket ticket, String titre, String message) {

        Notification notification = new Notification();
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setDateEnvoi(LocalDateTime.now());
        notification.setStatut(StatutNotification.ENVOYE);
        notification.setTicket(ticket);
        return notificationRepository.save(notification);
    }






}
