package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.ClientRequestDTO;
import org.example.smartqueue.dto.response.ClientResponseDTO;
import org.example.smartqueue.dto.response.UserResponseDTO;
import org.example.smartqueue.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    ClientResponseDTO createClient(ClientRequestDTO client);
    ClientResponseDTO getClientById(long id);
    ClientResponseDTO getClientByEmail(String email);

    ClientResponseDTO updateClient(long id,ClientRequestDTO client);
    void deleteClient(long id);
    Page<ClientResponseDTO> getAllClientsPaginated(Pageable pageable);

}
