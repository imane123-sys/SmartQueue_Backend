package org.example.smartqueue.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.TicketRequestDTO;
import org.example.smartqueue.dto.response.TicketResponseDTO;
import org.example.smartqueue.entity.Services;
import org.example.smartqueue.entity.Ticket;
import org.example.smartqueue.enums.StatutTicket;
import org.example.smartqueue.mapper.TicketMapper;
import org.example.smartqueue.repository.ClientRepository;
import org.example.smartqueue.repository.ServiceRepository;
import org.example.smartqueue.repository.TicketRepository;
import org.example.smartqueue.service.TicketService;
import org.springframework.stereotype.Service;

//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//@RequiredArgsConstructor
//@Service
//public class TicketServiceImp implements TicketService {
//    private final ClientRepository clientRepository;
//    private final ServiceRepository serviceRepository;
//    private final TicketRepository ticketRepository;
//    private final TicketMapper ticketMapper;
//    private final NotificationServiceImp notificationServiceImp;
//    @Override
//     public TicketResponseDTO reserveTicket(TicketRequestDTO ticket,String nom){
//        int nombre = ThreadLocalRandom.current().nextInt(1000, 10000);
//        Ticket tickets = new Ticket();
//        Services services = serviceRepository.findById(ticket.getServiceId()).get();
//        tickets.setServices(services);
//        tickets.setClient(clientRepository.findById(ticket.getClientId()).get());
//        tickets.setNumero(nombre);
//        tickets.setDateCreation(LocalDateTime.now());
//        tickets.setPosition(services.getTickets().size()+1);
//        tickets.setStatut(StatutTicket.EN_ATTENTE);
//        tickets.setQrCode("");
//        tickets.setTempsEstime(getTicketsEnAttente().size() * services.getDureeMoyenne());
//        tickets.getNotifications().add(notificationServiceImp.saveNotification(tickets,"Ticket reservé","Votre ticket a été réservé avec succès."));
//
//    }
//@Override
//    public List<TicketResponseDTO> getTicketsEnAttente(String nom){
//        return ticketMapper.toDTOList(ticketRepository.findByStatut_EnAttenteAndServices_Nom(nom));
//
//
//
//    }
//
//
//}
