package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.TicketRequestDTO;
import org.example.smartqueue.dto.response.TicketResponseDTO;
import org.example.smartqueue.entity.Client;
import org.example.smartqueue.entity.Services;
import org.example.smartqueue.entity.Ticket;
import org.example.smartqueue.enums.StatutTicket;
import org.example.smartqueue.mapper.TicketMapper;
import org.example.smartqueue.repository.ClientRepository;
import org.example.smartqueue.repository.ServiceRepository;
import org.example.smartqueue.repository.TicketRepository;
import org.example.smartqueue.service.imp.TicketServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock private ClientRepository clientRepository;
    @Mock private ServiceRepository serviceRepository;
    @Mock private TicketRepository ticketRepository;
    @Mock private TicketMapper ticketMapper;
    @Mock private NotificationService notificationService;

    @InjectMocks
    private TicketServiceImp ticketService;



    @Test
    @DisplayName("reserveTicket: should throw NoSuchElementException when service does not exist")
    void reserveTicket_WhenServiceNotFound_ShouldThrowNoSuchElementException() {
        TicketRequestDTO request = new TicketRequestDTO();
        request.setServiceId(999L);
        request.setClientEmail("client@test.com");

        when(serviceRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> ticketService.reserveTicket(request));
        verify(ticketRepository, never()).save(any());
    }





    @Test
    @DisplayName("appelerTicketSuivant: should throw RuntimeException when service does not exist")
    void appelerTicketSuivant_WhenServiceNotFound_ShouldThrowRuntimeException() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ticketService.appelerTicketSuivant(1L));
        verify(ticketRepository, never()).save(any());
    }



    @Test
    @DisplayName("suivreTicket: should throw RuntimeException when ticket does not exist")
    void suivreTicket_WhenTicketNotFound_ShouldThrowRuntimeException() {
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> ticketService.suivreTicket(999L));
        assertEquals("ce ticket n'existe pas", ex.getMessage());
    }
}
