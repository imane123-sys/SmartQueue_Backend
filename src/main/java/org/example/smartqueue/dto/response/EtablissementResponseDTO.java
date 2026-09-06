package org.example.smartqueue.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
@Getter
@Setter
public class EtablissementResponseDTO implements Serializable {
    private Long id;
    private String nom;
    private String adresse;
    private String telephone;
    private String type;
    private double latitude;
    private double longitude;
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime horaireOuverture;
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalTime horaireFermeture;

    private double distanceKm;
    private int tempsAttenteEstimeMinutes;
    private long nombrePersonnesEnAttente;
}