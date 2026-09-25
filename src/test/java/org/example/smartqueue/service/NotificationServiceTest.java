package org.example.smartqueue.service;

import org.example.smartqueue.entity.Client;
import org.example.smartqueue.entity.Notification;
import org.example.smartqueue.entity.Ticket;
import org.example.smartqueue.enums.Role;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock private NotificationRepository notificationRepository;
    @Mock private JavaMailSender mailSender;
    @Mock private TicketRepository ticketRepository;
    @Mock private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private NotificationServiceImp notificationService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(notificationService, "fromEmail", "noreply@smartqueue.com");
    }

    @Test
    @DisplayName("notificationConfirmation: should handle gracefully when services are null")
    void notificationConfirmation_WhenServiceOrEtablissementNull_ShouldHandleGracefully() {
        Client client = new Client();
        client.setId(1L);
        client.setEmail("user@test.com");

        Ticket ticket = new Ticket();
        ticket.setNumero(1025);
        ticket.setClient(client);
        ticket.setServices(null);

        assertDoesNotThrow(() -> notificationService.notificationConfirmation(ticket));

        verify(notificationRepository).save(argThat(n ->
                "Ticket créé".equals(n.getTitre()) &&
                        "Votre ticket a été créé.".equals(n.getMessage()) &&
                        n.getStatut() == StatutNotification.ENVOYE &&
                        n.getDestinataireRole() == Role.CLIENT
        ));
    }

    @Test
    @DisplayName("notificationUrTurn: should throw RuntimeException when ticket does not exist")
    void notificationUrTurn_WhenTicketNotFound_ShouldThrowRuntimeException() {
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> notificationService.notificationUrTurn(99L, 1L, null)
        );

        assertEquals("ce ticket n'existe pas ", exception.getMessage());
        verify(notificationRepository, never()).save(any());
    }
}