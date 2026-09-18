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
    public List<EtablissementResponseDTO> findEtablissementsProchesParServices(String serviceNom, double latitude, double longitude) {
        if (latitude == 0.0 && longitude == 0.0) {
            return List.of();
        }

        List<Etablissement> etablissements = etablissementRepository.findEtablissementsProchesParServices(serviceNom, latitude, longitude);
        List<EtablissementResponseDTO> dtos = etablissementMapper.toDTOList(etablissements);

        for (int i = 0; i < etablissements.size(); i++) {
            Etablissement e = etablissements.get(i);
            EtablissementResponseDTO dto = dtos.get(i);

            double distance = calculerDistanceKm(latitude, longitude, e.getLatitude(), e.getLongitude());
            dto.setDistanceKm(distance);
        }

        return dtos;
    }

    private double calculerDistanceKm(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = 6371 * c;
        return Math.round(dist * 10.0) / 10.0;
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
