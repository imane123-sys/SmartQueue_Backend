package org.example.smartqueue.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.TicketRequestDTO;
import org.example.smartqueue.dto.response.TicketResponseDTO;
import org.example.smartqueue.enums.StatutTicket;
import org.example.smartqueue.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/reserve")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<TicketResponseDTO> reserveTicket(
            @Valid @RequestBody TicketRequestDTO ticketRequestDTO) {

        return ResponseEntity.ok(ticketService.reserveTicket(ticketRequestDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ETABLISSEMENT', 'ADMIN')")
    public ResponseEntity<TicketResponseDTO> getTicketById(
            @PathVariable long id) {

        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @GetMapping("/attente")
    @PreAuthorize("hasAnyAuthority('ETABLISSEMENT', 'ADMIN')")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsEnAttente(
            @RequestParam StatutTicket statut,
            @RequestParam String nomService) {

        return ResponseEntity.ok(
                ticketService.getTicketsEnAttente(statut, nomService)
        );
    }

    @PutMapping("/annuler/{ticketid}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<TicketResponseDTO> annulerTicket(
            @PathVariable long ticketid,
            @RequestParam long clientId) {

        return ResponseEntity.ok(
                ticketService.annulerTicket(ticketid, clientId)
        );
    }

    @PutMapping("/appeler-suivant/{serviceId}")
    @PreAuthorize("hasAuthority('ETABLISSEMENT')")
    public ResponseEntity<TicketResponseDTO> appelerTicketSuivant(
            @PathVariable long serviceId) {

        return ResponseEntity.ok(
                ticketService.appelerTicketSuivant(serviceId)
        );
    }

    @PutMapping("/statut/{ticketid}")
    @PreAuthorize("hasAnyAuthority('ETABLISSEMENT', 'ADMIN')")
    public ResponseEntity<TicketResponseDTO> modifierStatut(
            @PathVariable long ticketid,
            @RequestParam StatutTicket nouveauStatut) {

        return ResponseEntity.ok(
                ticketService.modifierStatut(ticketid, nouveauStatut)
        );
    }

    @GetMapping("/historique/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Map<String, Long>> getHistorique(
            @PathVariable long id) {

        return ResponseEntity.ok(
                ticketService.getHistorique(id)
        );
    }

    @GetMapping("/suivre/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<TicketResponseDTO> suivreTicket(
            @PathVariable long id) {

        return ResponseEntity.ok(
                ticketService.suivreTicket(id)
        );
    }
}