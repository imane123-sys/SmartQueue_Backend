package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.ServiceRequestDTO;
import org.example.smartqueue.dto.response.ServiceResponseDTO;
import org.example.smartqueue.entity.Etablissement;
import org.example.smartqueue.entity.Services;
import org.example.smartqueue.mapper.ServiceMapper;
import org.example.smartqueue.repository.EtablissementRepository;
import org.example.smartqueue.repository.ServiceRepository;
import org.example.smartqueue.service.imp.ServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceImpTest {

    @Mock private ServiceRepository serviceRepository;
    @Mock private ServiceMapper serviceMapper;
    @Mock private EtablissementRepository etablissementRepository;

    @InjectMocks
    private ServiceImp serviceImp;

    // --- createService() Tests ---

    @Test
    @DisplayName("createService: should link etablissement, set duration and save service")
    void createService_WhenEtablissementExists_ShouldSaveAndReturnDTO() {
        ServiceRequestDTO request = new ServiceRequestDTO();
        request.setNom("Consultation");
        request.setDureeMoyenne(20);
        request.setEtablissementId(1L);

        Services serviceEntity = new Services();
        Etablissement etablissement = new Etablissement();
        etablissement.setId(1L);

        when(serviceMapper.toEntity(request)).thenReturn(serviceEntity);
        when(etablissementRepository.findById(1L)).thenReturn(Optional.of(etablissement));
        when(serviceRepository.save(serviceEntity)).thenReturn(serviceEntity);
        when(serviceMapper.toResponseDTO(serviceEntity)).thenReturn(new ServiceResponseDTO());

        ServiceResponseDTO result = serviceImp.createService(request);

        assertNotNull(result);
        assertEquals(etablissement, serviceEntity.getEtablissement());
        assertEquals(20, serviceEntity.getDureeMoyenne());
        verify(serviceRepository).save(serviceEntity);
    }

    @Test
    @DisplayName("createService: should throw NoSuchElementException when etablissement not found")
    void createService_WhenEtablissementNotFound_ShouldThrowNoSuchElementException() {
        ServiceRequestDTO request = new ServiceRequestDTO();
        request.setEtablissementId(99L);
        when(serviceMapper.toEntity(request)).thenReturn(new Services());
        when(etablissementRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> serviceImp.createService(request));
        verify(serviceRepository, never()).save(any());
    }

    // --- updateService() Tests ---

    @Test
    @DisplayName("updateService: should update existing service and return DTO")
    void updateService_WhenServiceExists_ShouldUpdateAndReturnDTO() {
        ServiceRequestDTO request = new ServiceRequestDTO();
        request.setNom("Nouveau Nom");

        Services existingService = new Services();
        existingService.setId(1L);
        existingService.setNom("Ancien Nom");

        when(serviceRepository.findById(1L)).thenReturn(Optional.of(existingService));
        when(serviceRepository.save(existingService)).thenReturn(existingService);
        when(serviceMapper.toResponseDTO(existingService)).thenReturn(new ServiceResponseDTO());

        ServiceResponseDTO result = serviceImp.updateService(1L, request);

        assertNotNull(result);
        verify(serviceMapper).updateEntityFromDto(request, existingService);
        verify(serviceRepository).save(existingService);
    }

    @Test
    @DisplayName("updateService: should throw RuntimeException when service does not exist")
    void updateService_WhenServiceNotFound_ShouldThrowRuntimeException() {
        ServiceRequestDTO request = new ServiceRequestDTO();
        when(serviceRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> serviceImp.updateService(99L, request));
        assertEquals("ce service n'existe pas", ex.getMessage());
        verify(serviceRepository, never()).save(any());
    }
}
