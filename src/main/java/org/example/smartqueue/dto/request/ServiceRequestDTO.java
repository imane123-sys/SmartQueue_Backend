package org.example.smartqueue.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRequestDTO {
    @NotBlank
    private String nom;

    private String description;

    @Min(1)
    private int dureeMoyenne;

    @NotNull
    private Long etablissementId;
}