package org.example.smartqueue.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "duree_moyenne")
    private int dureeMoyenne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etablissement_id", nullable = false)
    private Etablissement etablissement;
    @OneToMany(mappedBy ="service")
     private List<Ticket> tickets= new ArrayList<>();
}