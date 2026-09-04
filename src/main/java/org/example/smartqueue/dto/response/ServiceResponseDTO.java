package org.example.smartqueue.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter

public class ServiceResponseDTO implements Serializable {
        private Long id;
        private String nom;
        private String description;
        private int dureeMoyenne;
        private Long etablissementId;
        private String nomEtablissement;
    }
