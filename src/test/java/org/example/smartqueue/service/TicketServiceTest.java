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
    @DisplayName("reserveTicket: should reserve ticket, generate QR and notify client")
    void reserveTicket_WhenValidRequest_ShouldCreateTicketAndReturnDTO() {
        TicketRequestDTO request = new TicketRequestDTO();
        request.setServiceId(1L);
        request.setClientEmail("client@test.com");

        Services service = new Services();
        service.setId(1L);
        service.setDureeMoyenne(15);
        service.setTickets(new ArrayList<>());

        Client client = new Client();
        client.setId(10L);
        client.setEmail("client@test.com");

        TicketResponseDTO expectedDto = new TicketResponseDTO();

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(clientRepository.findByEmail("client@test.com")).thenReturn(Optional.of(client));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(ticketMapper.toResponseDTO(any(Ticket.class))).thenReturn(expectedDto);

        TicketResponseDTO result = ticketService.reserveTicket(request);

        assertNotNull(result);
        verify(ticketRepository).save(argThat(t -> 
            t.getPosition() == 1 &&
            t.getTempsEstime() == 15 &&
            t.getStatut() == StatutTicket.EN_ATTENTE &&
            t.getQrCode() != null
        ));
        verify(notificationService).notificationConfirmation(any(Ticket.class));
    }

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
    @DisplayName("annulerTicket: should mark ticket as ABSENT when client is owner")
    void annulerTicket_WhenClientMatches_ShouldSetStatusAbsent() {
        Client client = new Client();
        client.setId(5L);

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setClient(client);
        ticket.setStatut(StatutTicket.EN_ATTENTE);

        TicketResponseDTO expectedDto = new TicketResponseDTO();

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        when(ticketMapper.toResponseDTO(ticket)).thenReturn(expectedDto);

        TicketResponseDTO result = ticketService.annulerTicket(1L, 5L);

        assertNotNull(result);
        assertEquals(StatutTicket.ABSENT, ticket.getStatut());
        verify(ticketRepository).save(ticket);
    }

    @Test
    @DisplayName("annulerTicket: should throw RuntimeException when client is not ticket owner")
    void annulerTicket_WhenClientDoesNotMatch_ShouldThrowRuntimeException() {
        Client owner = new Client();
        owner.setId(5L);

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setClient(owner);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        RuntimeException ex = assertThrows(RuntimeException.class, 
            () -> ticketService.annulerTicket(1L, 99L)
        );
        assertEquals("Ce ticket ne vous appartient pas", ex.getMessage());
        verify(ticketRepository, never()).save(any());
    }


    @Test
    @DisplayName("appelerTicketSuivant: should change status of next ticket to EN_COURS")
    void appelerTicketSuivant_WhenQueueNotEmpty_ShouldSetStatusEnCours() {
        Services service = new Services();
        service.setId(1L);

        Ticket nextTicket = new Ticket();
        nextTicket.setId(10L);
        nextTicket.setStatut(StatutTicket.EN_ATTENTE);

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(ticketRepository.findByServicesIdAndStatutOrderByPositionAsc(1L, StatutTicket.EN_ATTENTE))
                .thenReturn(List.of(nextTicket));
        when(ticketRepository.save(nextTicket)).thenReturn(nextTicket);
        when(ticketMapper.toResponseDTO(nextTicket)).thenReturn(new TicketResponseDTO());

        TicketResponseDTO result = ticketService.appelerTicketSuivant(1L);

        assertNotNull(result);
        assertEquals(StatutTicket.EN_COURS, nextTicket.getStatut());
        verify(ticketRepository).save(nextTicket);
    }

    @Test
    @DisplayName("appelerTicketSuivant: should throw RuntimeException when service does not exist")
    void appelerTicketSuivant_WhenServiceNotFound_ShouldThrowRuntimeException() {
        when(serviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> ticketService.appelerTicketSuivant(1L));
        verify(ticketRepository, never()).save(any());
    }

    // --- suivreTicket() Tests ---

    @Test
    @DisplayName("suivreTicket: should recalculate position and estimated time")
    void suivreTicket_WhenTicketExists_ShouldRecalculatePositionAndTime() {
        Services service = new Services();
        service.setId(2L);
        service.setDureeMoyenne(10);

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setStatut(StatutTicket.EN_ATTENTE);
        ticket.setPosition(5);
        ticket.setServices(service);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.countByServicesIdAndStatutAndPositionLessThan(2L, StatutTicket.EN_ATTENTE, 5))
                .thenReturn(2L); // 2 tickets ahead
        when(ticketMapper.toResponseDTO(ticket)).thenReturn(new TicketResponseDTO());

        TicketResponseDTO result = ticketService.suivreTicket(1L);

        assertNotNull(result);
        assertEquals(3, ticket.getPosition()); // 2 + 1
        assertEquals(30, ticket.getTempsEstime()); // 3 * 10
    }

    @Test
    @DisplayName("suivreTicket: should throw RuntimeException when ticket does not exist")
    void suivreTicket_WhenTicketNotFound_ShouldThrowRuntimeException() {
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> ticketService.suivreTicket(999L));
        assertEquals("ce ticket n'existe pas", ex.getMessage());
    }
}
