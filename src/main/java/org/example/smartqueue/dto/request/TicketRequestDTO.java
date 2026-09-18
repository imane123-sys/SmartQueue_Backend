package org.example.smartqueue.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequestDTO {
    @NotNull(message = "L'email du client est obligatoire")
    private String clientEmail;

    @NotNull(message = "L'identifiant du service est obligatoire")
    private Long serviceId;
}
