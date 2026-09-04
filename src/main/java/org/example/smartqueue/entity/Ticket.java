package org.example.smartqueue.entity;
import jakarta.persistence.*;
import lombok.*;
import org.example.smartqueue.enums.StatutTicket;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "qr_code")
    private String qrCode;

    private int position;

    @Column(name = "temps_estime")
    private int tempsEstime;

    @Enumerated(EnumType.STRING)
    private StatutTicket statut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;
}