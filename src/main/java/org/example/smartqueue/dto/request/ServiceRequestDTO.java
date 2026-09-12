package org.example.smartqueue.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRequestDTO {
    @NotBlank(message = "Le nom du service est obligatoire")
    private String nom;

    private String description;

    @Min(value = 1, message = "La durée moyenne doit être d'au moins 1 minute")
    private int dureeMoyenne;

    @NotNull(message = "L'établissement est obligatoire")
    private Long etablissementId;
}