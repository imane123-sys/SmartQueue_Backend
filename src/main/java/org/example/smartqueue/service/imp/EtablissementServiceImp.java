package org.example.smartqueue.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.response.EtablissementResponseDTO;
import org.example.smartqueue.dto.response.NotificationResponseDTO;
import org.example.smartqueue.entity.Etablissement;
import org.example.smartqueue.entity.Notification;
import org.example.smartqueue.enums.Role;
import org.example.smartqueue.mapper.ClientMapper;
import org.example.smartqueue.mapper.EtablissementMapper;
import org.example.smartqueue.repository.EtablissementRepository;
import org.example.smartqueue.service.EtablissementService;
import org.example.smartqueue.service.GeocodingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public  class EtablissementServiceImp implements EtablissementService {
    private final EtablissementMapper etablissementMapper;
    private final EtablissementRepository etablissementRepository;
    private final ClientMapper clientMapper;
    private final GeocodingService geocodingService;
    private final PasswordEncoder passwordEncoder;

    @Override
     public List<EtablissementResponseDTO> findEtablissementsProchesParServices(String serviceNom,double latitude,double longitude,double rayonKm){
         List <Etablissement> etablissements = etablissementRepository.findEtablissementsProchesParServices(serviceNom, latitude, longitude, rayonKm);
         return etablissementMapper.toDTOList(etablissements);
    }
    @Override
    public EtablissementResponseDTO getEtablissementById(long id){
         Etablissement etablissement =etablissementRepository.findById(id).orElseThrow(()->new RuntimeException("cl'établissment avec ce id n'existe pas"));
         return etablissementMapper.toResponseDTO(etablissement);

    }
    @Override
    public Page<EtablissementResponseDTO> getAllEtablissements(Pageable pageable){
        Page<Etablissement> etablissements =etablissementRepository.findAll(pageable);
        return etablissements.map(etablissementMapper::toResponseDTO);

    }
    @Override
     public EtablissementResponseDTO updateEtablissement(long id, EtablissementRequestDTO etablissementRequestDTO){
         Etablissement etablissement=etablissementRepository.findById(id).orElseThrow(()->new RuntimeException("cet établissement n'existe pas"));
              etablissementMapper.updateEntityFromDto(etablissementRequestDTO,etablissement );
                return etablissementMapper.toResponseDTO(etablissementRepository.save(etablissement));



   }

    @Override
    public EtablissementResponseDTO createEtablissement(EtablissementRequestDTO etablissement) {
        Etablissement etablissement1= etablissementMapper.toEntity(etablissement);
        double [] coordinates=geocodingService.getCoordinates(etablissement1.getAdresse());
        etablissement1.setLatitude(coordinates[0]);
        etablissement1.setLongitude(coordinates[1]);
        etablissement1.setRole(Role.ETABLISSEMENT);
        etablissement1.setPassword(passwordEncoder.encode(etablissement.getPassword()));

        Etablissement etablissement2 =etablissementRepository.save(etablissement1);
                return etablissementMapper.toResponseDTO(etablissement2);

    }
    @Override
     public void deleteEtablissement(long id){
         etablissementRepository.deleteById(id);

    }









}
