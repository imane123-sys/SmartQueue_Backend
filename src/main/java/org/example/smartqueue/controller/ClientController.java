package org.example.smartqueue.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.ClientRequestDTO;
import org.example.smartqueue.dto.response.ClientResponseDTO;
import org.example.smartqueue.service.ClientService;
import org.example.smartqueue.service.imp.ClientServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;
    @PostMapping("/create")
    public ResponseEntity<ClientResponseDTO> createClient( @Valid @RequestBody ClientRequestDTO clientRequestDTO){
        ClientResponseDTO response =clientService.createClient(clientRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable long id){
        return ResponseEntity.ok(clientService.getClientById(id));

    }
    @GetMapping("/client")
    public ResponseEntity<ClientResponseDTO> getClientById(@RequestParam String email){
        return ResponseEntity.ok(clientService.getClientByEmail(email));

    }
    @GetMapping
    public ResponseEntity <List<ClientResponseDTO>>getAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity <ClientResponseDTO>updateClient(@PathVariable long id ,@RequestBody ClientRequestDTO clientRequestDTO){
        return ResponseEntity.ok(clientService.updateClient(id,clientRequestDTO));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity <Void>deleteClient(@PathVariable long id){
         clientService.deleteClient(id);
         return ResponseEntity.noContent().build();
    }
}
