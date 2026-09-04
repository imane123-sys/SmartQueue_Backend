package org.example.smartqueue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.smartqueue.enums.StatutTicket;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO implements Serializable {
    private Long id;
    private int numero;
    private LocalDateTime dateCreation;
    private String qrCode;
    private int position;
    private int tempsEstime;
    private StatutTicket statut;
    private Long clientId;
    private String nomClient;
    private Long serviceId;
    private String nomService;
    private String nomEtablissement;
}
