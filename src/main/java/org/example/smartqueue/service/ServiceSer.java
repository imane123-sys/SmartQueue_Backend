package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.ServiceRequestDTO;
import org.example.smartqueue.dto.response.ServiceResponseDTO;
import org.example.smartqueue.entity.Services;
import org.mapstruct.MappingTarget;

import java.util.List;

public interface ServiceSer {
    List<ServiceResponseDTO> getServicesByEtablissementById(long id);
     Boolean existsByNomAndEtablissementId(String nom,long id);
    ServiceResponseDTO createService(ServiceRequestDTO service);
    ServiceResponseDTO updateService(long id,ServiceRequestDTO service);
    void delete(long id);
    List<ServiceResponseDTO>getALlServices();


}
