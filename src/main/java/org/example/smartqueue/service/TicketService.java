package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.TicketRequestDTO;
import org.example.smartqueue.dto.response.TicketResponseDTO;
import org.example.smartqueue.enums.StatutTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TicketService {
    TicketResponseDTO reserveTicket(TicketRequestDTO ticket);
    TicketResponseDTO suivreTicket(long id);
    Map<String ,Long> getHistorique(long id);
    List<TicketResponseDTO> getTicketsEnAttente(StatutTicket statut ,String nomService);
    TicketResponseDTO annulerTicket(long ticketid,long clientId);
    TicketResponseDTO appelerTicketSuivant(long serviceId);
    TicketResponseDTO modifierStatut(long ticketid,StatutTicket nouveauStatut);
    TicketResponseDTO getTicketById(long id);
    String generateQR(String text)  throws Exception ;
     Page <TicketResponseDTO> getTicketsByEtablissmentid(StatutTicket statut ,Long id , Pageable pageable);


    }
