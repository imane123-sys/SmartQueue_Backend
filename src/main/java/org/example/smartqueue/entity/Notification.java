package org.example.smartqueue.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.smartqueue.enums.StatutNotification;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message="titre est obligatoire")
    private String titre;
    @NotBlank(message="message est obligatoire")
    private String message;

    @Column(name = "date_envoi")
    private LocalDateTime dateEnvoi;

    @Enumerated(EnumType.STRING)
    private StatutNotification statut;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;
}