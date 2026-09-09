package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.TicketRequestDTO;
import org.example.smartqueue.dto.response.TicketResponseDTO;
import org.example.smartqueue.enums.StatutTicket;

import java.util.List;

public interface TicketService {
    TicketResponseDTO reserveTicket(TicketRequestDTO ticket);
    TicketResponseDTO suivreTicket(long id);
    List<TicketResponseDTO>getHistorique(long id);
    List<TicketResponseDTO> getTicketsEnAttente();
    List<TicketResponseDTO>annulerTicket(long ticketid,long clientId);
    TicketResponseDTO appelerTicketSuivant(long serviceId);
    TicketResponseDTO modifierStatut(long id, StatutTicket nouveauStatut);
    TicketResponseDTO getTicketById(long id);

}
