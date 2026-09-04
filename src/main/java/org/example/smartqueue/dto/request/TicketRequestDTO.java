package org.example.smartqueue.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketRequestDTO {
    @NotNull
    private Long clientId;

    @NotNull
    private Long serviceId;
}
