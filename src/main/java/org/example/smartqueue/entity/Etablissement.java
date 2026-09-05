package org.example.smartqueue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "etablissements")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Etablissement extends User {

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