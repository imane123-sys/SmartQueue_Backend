package org.example.smartqueue.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "etablissements")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Etablissement extends User {

    @Column(nullable = false)
    private String adresse;

    private String telephone;
    private String type;
    private double latitude;
    private double longitude;

    private LocalTime horaireOuverture;
    private LocalTime horaireFermeture;
    @OneToMany(mappedBy ="etablissement" )
    private List<Services> services= new ArrayList<>();
}