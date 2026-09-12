package org.example.smartqueue.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.ClientRequestDTO;
import org.example.smartqueue.dto.response.ClientResponseDTO;
import org.example.smartqueue.service.ClientService;
import org.example.smartqueue.service.imp.ClientServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClientResponseDTO> createClient( @Valid @RequestBody ClientRequestDTO clientRequestDTO){
        ClientResponseDTO response =clientService.createClient(clientRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable long id){
        return ResponseEntity.ok(clientService.getClientById(id));

    }
    @GetMapping("/client")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ClientResponseDTO> getClientById(@RequestParam String email){
        return ResponseEntity.ok(clientService.getClientByEmail(email));

    }
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public  ResponseEntity<Page<ClientResponseDTO>>getAllClientsPaginated(@PageableDefault(page =0 , size =10, sort="nom", direction= Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(clientService.getAllClientsPaginated(pageable));
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT')")
    public ResponseEntity <ClientResponseDTO>updateClient(@PathVariable long id ,@RequestBody ClientRequestDTO clientRequestDTO){
        return ResponseEntity.ok(clientService.updateClient(id,clientRequestDTO));
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity <Void>deleteClient(@PathVariable long id){
         clientService.deleteClient(id);
         return ResponseEntity.noContent().build();
    }
    @GetMapping("/clients")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<ClientResponseDTO>>getAllClient(Pageable pageable){
        return ResponseEntity.ok(clientService.getAllClientsPaginated(pageable));
    }



}
