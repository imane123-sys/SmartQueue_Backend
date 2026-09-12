package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.response.EtablissementResponseDTO;
import org.example.smartqueue.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EtablissementService {
    List<EtablissementResponseDTO>findEtablissementsProchesParServices(String serviceNom,double latitude,double longitude,double rayonKm);
    EtablissementResponseDTO getEtablissementById(long id);
    Page<EtablissementResponseDTO> getAllEtablissements(Pageable pageable);
    EtablissementResponseDTO updateEtablissement(long id,EtablissementRequestDTO etablissement);
    EtablissementResponseDTO createEtablissement(EtablissementRequestDTO etablissement);
    void deleteEtablissement(long id);

}
