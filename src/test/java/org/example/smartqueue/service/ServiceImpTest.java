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
