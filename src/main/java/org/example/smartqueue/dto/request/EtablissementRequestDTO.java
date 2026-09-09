package org.example.smartqueue.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "08:30")

    private LocalTime horaireOuverture;
    @JsonFormat(pattern = "HH:mm", shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "18:30")

    private LocalTime horaireFermeture;
}