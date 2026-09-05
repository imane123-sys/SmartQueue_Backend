package org.example.smartqueue.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;


@Getter
@Setter
public class EtablissementRequestDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
    @NotBlank
    private String nom;

    @NotBlank
    private String adresse;

    private String telephone;
    private String type;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private LocalTime horaireOuverture;
    private LocalTime horaireFermeture;
}