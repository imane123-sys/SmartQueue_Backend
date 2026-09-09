package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.response.EtablissementResponseDTO;

import java.util.List;

public interface EtablissementService {
    List<EtablissementResponseDTO>findEtablissementsProchesParServices(String serviceNom,double latitude,double longitude,double rayonKm);
    EtablissementResponseDTO getEtablissementById(long id);
    List<EtablissementResponseDTO> getAllEtablissements();
    EtablissementResponseDTO updateEtablissement(long id,EtablissementRequestDTO etablissement);
    EtablissementResponseDTO createEtablissement(EtablissementRequestDTO etablissement);
    void deleteEtablissement(long id);

}
