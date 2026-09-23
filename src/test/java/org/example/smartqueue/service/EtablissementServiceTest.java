package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.response.EtablissementResponseDTO;
import org.example.smartqueue.entity.Etablissement;
import org.example.smartqueue.enums.Role;
import org.example.smartqueue.mapper.EtablissementMapper;
import org.example.smartqueue.repository.EtablissementRepository;
import org.example.smartqueue.service.imp.EtablissementServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtablissementServiceTest {

    @Mock private EtablissementMapper etablissementMapper;
    @Mock private EtablissementRepository etablissementRepository;
    @Mock private GeocodingService geocodingService;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EtablissementServiceImp etablissementService;


    @Test
    @DisplayName("createEtablissement: should fetch coordinates, encode password and save")
    void createEtablissement_WhenValidRequest_ShouldGeocodeAndSave() {
        EtablissementRequestDTO request = new EtablissementRequestDTO();
        request.setPassword("secret123");

        Etablissement entity = new Etablissement();
        entity.setAdresse("10 Rue de Paris");

        when(etablissementMapper.toEntity(request)).thenReturn(entity);
        when(geocodingService.getCoordinates("10 Rue de Paris")).thenReturn(new double[]{48.8566, 2.3522});
        when(passwordEncoder.encode("secret123")).thenReturn("encodedSecret");
        when(etablissementRepository.save(entity)).thenReturn(entity);
        when(etablissementMapper.toResponseDTO(entity)).thenReturn(new EtablissementResponseDTO());

        EtablissementResponseDTO result = etablissementService.createEtablissement(request);

        assertNotNull(result);
        assertEquals(48.8566, entity.getLatitude());
        assertEquals(2.3522, entity.getLongitude());
        assertEquals(Role.ETABLISSEMENT, entity.getRole());
        assertEquals("encodedSecret", entity.getPassword());
        verify(etablissementRepository).save(entity);
    }

    @Test
    @DisplayName("createEtablissement: should throw exception when geocoding fails")
    void createEtablissement_WhenGeocodingFails_ShouldThrowRuntimeException() {
        EtablissementRequestDTO request = new EtablissementRequestDTO();
        Etablissement entity = new Etablissement();
        entity.setAdresse("Adresse Invalide");

        when(etablissementMapper.toEntity(request)).thenReturn(entity);
        when(geocodingService.getCoordinates("Adresse Invalide")).thenThrow(new RuntimeException("Adresse introuvable"));

        assertThrows(RuntimeException.class, () -> etablissementService.createEtablissement(request));
        verify(etablissementRepository, never()).save(any());
    }

    // --- findEtablissementsProchesParServices() Tests ---

    @Test
    @DisplayName("findEtablissementsProchesParServices: should calculate distance correctly for nearby establishments")
    void findEtablissementsProchesParServices_WhenValidCoordinates_ShouldCalculateDistances() {
        Etablissement e1 = new Etablissement();
        e1.setLatitude(48.8566);
        e1.setLongitude(2.3522);

        EtablissementResponseDTO dto1 = new EtablissementResponseDTO();
        List<Etablissement> list = List.of(e1);
        List<EtablissementResponseDTO> dtos = new ArrayList<>(List.of(dto1));

        when(etablissementRepository.findEtablissementsProchesParServices("dentiste", 48.8500, 2.3500))
                .thenReturn(list);
        when(etablissementMapper.toDTOList(list)).thenReturn(dtos);

        List<EtablissementResponseDTO> result = etablissementService.findEtablissementsProchesParServices("dentiste", 48.8500, 2.3500);

        assertEquals(1, result.size());
        assertTrue(result.get(0).getDistanceKm() > 0);
    }

    @Test
    @DisplayName("findEtablissementsProchesParServices: should return empty list when coordinates are (0.0, 0.0)")
    void findEtablissementsProchesParServices_WhenCoordinatesZero_ShouldReturnEmptyList() {
        List<EtablissementResponseDTO> result = etablissementService.findEtablissementsProchesParServices("dentiste", 0.0, 0.0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(etablissementRepository, never()).findEtablissementsProchesParServices(any(), anyDouble(), anyDouble());
    }
}
