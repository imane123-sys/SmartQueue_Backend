package org.example.smartqueue.service.imp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.TicketRequestDTO;
import org.example.smartqueue.dto.response.TicketResponseDTO;
import org.example.smartqueue.entity.Notification;
import org.example.smartqueue.entity.Services;
import org.example.smartqueue.entity.Ticket;
import org.example.smartqueue.enums.StatutTicket;
import org.example.smartqueue.mapper.TicketMapper;
import org.example.smartqueue.repository.ClientRepository;
import org.example.smartqueue.repository.ServiceRepository;
import org.example.smartqueue.repository.TicketRepository;
import org.example.smartqueue.service.NotificationService;
import org.example.smartqueue.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class TicketServiceImp implements TicketService {
    private final ClientRepository clientRepository;
    private final ServiceRepository serviceRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final NotificationService notificationService;
    @Override
     public TicketResponseDTO reserveTicket(TicketRequestDTO ticket){
        int nombre = ThreadLocalRandom.current().nextInt(1000, 10000);
        Ticket tickets = new Ticket();
        Services services = serviceRepository.findById(ticket.getServiceId()).get();
        tickets.setServices(services);
        tickets.setClient(clientRepository.findById(ticket.getClientId()).get());
        tickets.setNumero(nombre);
        tickets.setDateCreation(LocalDateTime.now());
        tickets.setPosition(services.getTickets().size()+1);
        tickets.setStatut(StatutTicket.EN_ATTENTE);

        try {
            tickets.setQrCode(generateQR("TICKET-" + tickets.getNumero()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int duree = services.getDureeMoyenne() > 0 ? services.getDureeMoyenne() : 10;



        tickets.setTempsEstime(tickets.getPosition() * duree);

        Ticket savedTicket = ticketRepository.save(tickets);
        Notification notification = notificationService.saveNotification(
                savedTicket,
                "Ticket réservé",
                "Votre ticket a été réservé avec succès."
        );
        savedTicket.getNotifications().add(notification);
        return ticketMapper.toResponseDTO(ticketRepository.save(tickets));

    }
    @Override
    public String generateQR(String text) throws Exception {
        BitMatrix matrix = new MultiFormatWriter()
                .encode(text, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", out);

        return Base64.getEncoder().encodeToString(out.toByteArray());
    }
@Override
    public List<TicketResponseDTO> getTicketsEnAttente(StatutTicket statut ,String nomService){
        return ticketMapper.toDTOList(ticketRepository.findByStatutAndServices_Nom(statut ,nomService));

    }
    @Override
    public TicketResponseDTO annulerTicket(long ticketid,long clientId){
        Ticket ticket= ticketRepository.findById(ticketid).orElseThrow(()->new RuntimeException("ce ticket n'existe pas"));
         if (ticket.getClient().getId() != clientId){
             throw new RuntimeException("Ce ticket ne vous appartient pas");
         }
        ticket.setStatut(StatutTicket.ABSENT);
        return ticketMapper.toResponseDTO(ticket);

    }
    @Override
    public TicketResponseDTO appelerTicketSuivant(long serviceId){
        Services services = serviceRepository.findById(serviceId).orElseThrow(()->new RuntimeException("ce service n'appartient pas à cet établissement"));
        Ticket ticket = ticketRepository.findByServicesIdAndStatutOrderByPositionAsc(services.getId(),StatutTicket.EN_ATTENTE).get(0);
        ticket.setStatut(StatutTicket.EN_COURS);
        return ticketMapper.toResponseDTO( ticketRepository.save(ticket));

    }
    @Override
    public TicketResponseDTO modifierStatut(long ticketid,StatutTicket nouveauStatut){
        Ticket ticket= ticketRepository.findById(ticketid).orElseThrow(()->new RuntimeException("ce ticket n'existe pas"));
        ticket.setStatut(nouveauStatut);
        return ticketMapper.toResponseDTO(ticket);

    }
    @Override
     public TicketResponseDTO getTicketById(long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new RuntimeException("ce ticket n'existe pas"));
        return ticketMapper.toResponseDTO(ticket);

    }
    @Override
    public Map<String ,Long> getHistorique(long id){
        Map<String,Long> status= new HashMap<>();
        status.put("countStatut_EN_ATTENTE",ticketRepository.countTicketsEtablissemnt(id,StatutTicket.EN_ATTENTE));
        status.put("countStatut_EN_COURS",ticketRepository.countTicketsEtablissemnt(id,StatutTicket.EN_COURS));
        status.put("countStatut_TERMINE",ticketRepository.countTicketsEtablissemnt(id,StatutTicket.TERMINE));
        status.put("countStatut_ABSENT",ticketRepository.countTicketsEtablissemnt(id,StatutTicket.ABSENT));
        return status;

    }
    @Override
     public TicketResponseDTO suivreTicket(long id){
         Ticket ticket= ticketRepository.findById(id).orElseThrow(()->new RuntimeException("ce ticket n'existe pas"));
         long personBefore = ticketRepository.countByServicesIdAndStatutAndPositionLessThan(ticket.getServices().getId(),ticket.getStatut(),ticket.getPosition());
         ticket.setPosition((int)personBefore+1);
         ticket.setTempsEstime(ticket.getServices().getDureeMoyenne() *ticket.getPosition());
         return ticketMapper.toResponseDTO(ticket);


     }
     @Override
    public Page<TicketResponseDTO> getTicketsByEtablissmentid(StatutTicket statut,Long id , Pageable pageable){
        Page<Ticket> tickets=ticketRepository.findByStatutAndServices_Etablissement_Id(StatutTicket.EN_ATTENTE,id,pageable);
        return tickets.map(ticketMapper::toResponseDTO);
    }






}
