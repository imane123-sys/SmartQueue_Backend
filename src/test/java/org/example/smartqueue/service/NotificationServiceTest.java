package org.example.smartqueue.service;

import org.example.smartqueue.entity.Client;
import org.example.smartqueue.entity.Etablissement;
import org.example.smartqueue.entity.Notification;
import org.example.smartqueue.entity.Services;
import org.example.smartqueue.entity.Ticket;
import org.example.smartqueue.enums.StatutNotification;
import org.example.smartqueue.repository.NotificationRepository;
import org.example.smartqueue.repository.TicketRepository;
import org.example.smartqueue.service.imp.NotificationServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock private NotificationRepository notificationRepository;
    @Mock private JavaMailSender mailSender;
    @Mock private TicketRepository ticketRepository;

    @InjectMocks
    private NotificationServiceImp notificationService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(notificationService, "fromEmail", "noreply@smartqueue.com");
    }


    @Test
    @DisplayName("notificationConfirmation: should save notification and send async email")
    void notificationConfirmation_WhenValidTicket_ShouldSaveNotificationAndSendEmail() {
        Client client = new Client();
        client.setEmail("user@test.com");

        Etablissement etablissement = new Etablissement();
        etablissement.setNom("Clinique Centrale");

        Services service = new Services();
        service.setNom("Pédiatrie");
        service.setEtablissement(etablissement);

        Ticket ticket = new Ticket();
        ticket.setNumero(1024);
        ticket.setClient(client);
        ticket.setServices(service);

        notificationService.notificationConfirmation(ticket);

        verify(notificationRepository).save(argThat(n -> 
            n.getTitre().equals("Confirmation de ticket") &&
            n.getStatut() == StatutNotification.ENVOYE &&
            n.getMessage().contains("1024")
        ));
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("notificationConfirmation: should handle gracefully when services are null")
    void notificationConfirmation_WhenServiceOrEtablissementNull_ShouldHandleGracefully() {
        Client client = new Client();
        client.setEmail("user@test.com");

        Ticket ticket = new Ticket();
        ticket.setNumero(1025);
        ticket.setClient(client);
        ticket.setServices(null);

        assertDoesNotThrow(() -> notificationService.notificationConfirmation(ticket));
        verify(notificationRepository).save(any(Notification.class));
    }


    @Test
    @DisplayName("notificationUrTurn: should save turn notification when ticket exists")
    void notificationUrTurn_WhenTicketExists_ShouldSaveUrTurnNotification() {
        Ticket ticket = new Ticket();
        ticket.setId(10L);

        when(ticketRepository.findById(10L)).thenReturn(Optional.of(ticket));

        notificationService.notificationUrTurn(10L, 1L, ticket);

        verify(notificationRepository).save(argThat(n ->
            n.getTitre().equals("C'est votre tour !") &&
            n.getStatut() == StatutNotification.ENVOYE &&
            n.getTicket().getId().equals(10L)
        ));
    }

    @Test
    @DisplayName("notificationUrTurn: should throw RuntimeException when ticket does not exist")
    void notificationUrTurn_WhenTicketNotFound_ShouldThrowRuntimeException() {
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> notificationService.notificationUrTurn(99L, 1L, new Ticket()));
        verify(notificationRepository, never()).save(any());
    }
}
