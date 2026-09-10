package org.example.smartqueue.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.ServiceRequestDTO;
import org.example.smartqueue.dto.response.ServiceResponseDTO;
import org.example.smartqueue.entity.Etablissement;
import org.example.smartqueue.entity.Services;
import org.example.smartqueue.mapper.ServiceMapper;
import org.example.smartqueue.repository.EtablissementRepository;
import org.example.smartqueue.repository.ServiceRepository;
import org.example.smartqueue.service.ServiceSer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public  class ServiceImp implements ServiceSer {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final EtablissementRepository etablissementRepository;

    @Override
    public List<ServiceResponseDTO>getServicesByEtablissementById(long id) {
        List<Services> services = serviceRepository.findByEtablissementId(id);
        return serviceMapper.toDTOList(services);
    }
    @Override
    public  Boolean existsByNomAndEtablissementId(String nom,long id) {
        return serviceRepository.existsByNomAndEtablissementId(nom,id);
    }
@Override
 public ServiceResponseDTO createService(ServiceRequestDTO service) {
    Services services =serviceMapper.toEntity(service);
    Etablissement etablissement = etablissementRepository.findById(service.getEtablissementId()).get();
    services.setEtablissement(etablissement);
    services.setDureeMoyenne(service.getDureeMoyenne());
    return serviceMapper.toResponseDTO(serviceRepository.save(services)) ;
}


    @Override
    public ServiceResponseDTO updateService(long id, ServiceRequestDTO service) {
        Services services= serviceRepository.findById(id).orElseThrow(()->new RuntimeException("ce service n'existe pas"));
         serviceMapper.updateEntityFromDto(service,services);
        return serviceMapper.toResponseDTO(serviceRepository.save(services));


    }

    @Override
    public void delete(long id) {
        serviceRepository.deleteById(id);

    }
    @Override
    public List<ServiceResponseDTO>getALlServices(){
        List<Services> services= serviceRepository.findAll();
        return serviceMapper.toDTOList(services);
    }
}









