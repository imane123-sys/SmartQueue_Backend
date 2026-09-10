package org.example.smartqueue.service.imp;

import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.ClientRequestDTO;
import org.example.smartqueue.dto.response.ClientResponseDTO;
import org.example.smartqueue.dto.response.UserResponseDTO;
import org.example.smartqueue.entity.Client;
import org.example.smartqueue.enums.Role;
import org.example.smartqueue.mapper.ClientMapper;
import org.example.smartqueue.repository.ClientRepository;
import org.example.smartqueue.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public  class ClientServiceImp implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    @Override
     public ClientResponseDTO createClient(ClientRequestDTO client){
        Client client1= clientMapper.toEntity(client);
        client1.setRole(Role.CLIENT);
        Client clientt= clientRepository.save(client1);
                return clientMapper.toDto(clientt);
    }
    @Override
     public ClientResponseDTO getClientById(long id){
        Client client =clientRepository.findById(id).orElseThrow();
        return clientMapper.toDto(client);

    }
    @Override
    public ClientResponseDTO getClientByEmail(String email){
        Client client = clientRepository.findByEmail(email).get();
        return clientMapper.toDto(client);
    }
    @Override
     public List<ClientResponseDTO> getAllClients(){
        List<Client> clients=clientRepository.findAll();
        return clientMapper.toDTOList(clients);
    }
    @Override
//     public ClientResponseDTO updateClient(long id,ClientRequestDTO clientRequestDTO){
//        ClientRequestDTO client= new ClientRequestDTO();
//        client.setNom(clientRequestDTO.getNom());
//        client.setEmail(clientRequestDTO.getEmail());
//        client.setTelephone(clientRequestDTO.getTelephone());
//        client.setPassword(clientRequestDTO.getPassword());
//         Client client1 =clientMapper.toEntity(client);
//         return clientMapper.toDto( clientRepository.save(client1));
//
//    }

    public ClientResponseDTO updateClient(long id,ClientRequestDTO clientRequestDTO){
        Client client= clientRepository.findById(id).get();
        clientMapper.updateEntityFromDTO(clientRequestDTO,client);
        return clientMapper.toDto(clientRepository.save(client));

    }
    @Override
    public void deleteClient(long id){
        clientRepository.deleteById(id);
    }







}
