package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.ClientRequestDTO;
import org.example.smartqueue.dto.response.ClientResponseDTO;
import org.example.smartqueue.entity.Client;
import org.example.smartqueue.enums.Role;
import org.example.smartqueue.mapper.ClientMapper;
import org.example.smartqueue.repository.ClientRepository;
import org.example.smartqueue.service.imp.ClientServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock private ClientRepository clientRepository;
    @Mock private ClientMapper clientMapper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientServiceImp clientService;


    @Test
    @DisplayName("createClient: should encode password, set CLIENT role and save")
    void createClient_WhenValidRequest_ShouldEncodePasswordAndSave() {
        ClientRequestDTO request = new ClientRequestDTO();
        request.setPassword("plainTextPass");

        Client client = new Client();
        when(clientMapper.toEntity(request)).thenReturn(client);
        when(passwordEncoder.encode("plainTextPass")).thenReturn("hashedPass");
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.toDto(client)).thenReturn(new ClientResponseDTO());

        ClientResponseDTO response = clientService.createClient(request);

        assertNotNull(response);
        assertEquals(Role.CLIENT, client.getRole());
        assertEquals("hashedPass", client.getPassword());
        verify(clientRepository).save(client);
    }

    @Test
    @DisplayName("createClient: should propagate error when save fails")
    void createClient_WhenRepositoryFails_ShouldPropagateException() {
        ClientRequestDTO request = new ClientRequestDTO();
        request.setPassword("pass");
        Client client = new Client();

        when(clientMapper.toEntity(request)).thenReturn(client);
        when(passwordEncoder.encode("pass")).thenReturn("hash");
        when(clientRepository.save(client)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> clientService.createClient(request));
    }


    @Test
    @DisplayName("getClientById: should return ClientResponseDTO when client is found")
    void getClientById_WhenClientExists_ShouldReturnDTO() {
        Client client = new Client();
        client.setId(1L);

        ClientResponseDTO dto = new ClientResponseDTO();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(dto);

        ClientResponseDTO result = clientService.getClientById(1L);

        assertNotNull(result);
        verify(clientRepository).findById(1L);
    }

    @Test
    @DisplayName("getClientById: should throw NoSuchElementException when client does not exist")
    void getClientById_WhenClientNotFound_ShouldThrowNoSuchElementException() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> clientService.getClientById(99L));
    }
}
