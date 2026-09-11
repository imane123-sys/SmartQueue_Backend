package org.example.smartqueue.controller;

import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.response.NotificationResponseDTO;
import org.example.smartqueue.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/client/{idClient}")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByClient(
            @PathVariable long idClient) {

        return ResponseEntity.ok(
                notificationService.getNotificationsByClient(idClient)
        );
    }

    @PutMapping("/annuler-ticket/{id}")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Void> notificationAnnulationTicket(
            @PathVariable long id) {

        notificationService.notificationAnnulationTicket(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tour/{idTicket}/{idClient}")
    @PreAuthorize("hasAuthority('ETABLISSEMENT')")
    public ResponseEntity<Void> notificationUrTurn(
            @PathVariable long idTicket,
            @PathVariable long idClient) {

        notificationService.notificationUrTurn(idTicket, idClient, null);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tour-approche/{idTicket}")
    @PreAuthorize("hasAuthority('ETABLISSEMENT')")
    public ResponseEntity<Void> sendTurnApproachingNotification(
            @PathVariable long idTicket,
            @RequestParam long position,
            @RequestParam long tempsEstime) {

        notificationService.sendTurnApproachingNotification(
                idTicket, position, tempsEstime
        );

        return ResponseEntity.ok().build();
    }
}