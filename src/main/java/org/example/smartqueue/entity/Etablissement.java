package org.example.smartqueue.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "etablissements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Etablissement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adresse;

    private String telephone;
    private String type;
    private double latitude;
    private double longitude;

    private LocalTime horaireOuverture;
    private LocalTime horaireFermeture;
}
