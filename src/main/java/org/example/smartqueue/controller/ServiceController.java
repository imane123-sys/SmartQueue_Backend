package org.example.smartqueue.controller;




import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.ServiceRequestDTO;
import org.example.smartqueue.dto.response.ServiceResponseDTO;
import org.example.smartqueue.service.ServiceSer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceSer serviceService;

    @GetMapping("/etablissement")
    public ResponseEntity<List<ServiceResponseDTO>> getServicesByEtablissementId(
            @RequestParam long id) {

        return ResponseEntity.ok(
                serviceService.getServicesByEtablissementById(id)
        );
    }

    @GetMapping("/exists")
    @PreAuthorize("hasAnyAuthority('ETABLISSEMENT', 'ADMIN')")
    public ResponseEntity<Boolean> existsByNomAndEtablissementId(
            @RequestParam String nom,
            @RequestParam long id) {

        return ResponseEntity.ok(
                serviceService.existsByNomAndEtablissementId(nom, id)
        );
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('ETABLISSEMENT', 'ADMIN')")
    public ResponseEntity<ServiceResponseDTO> createService(
            @Valid @RequestBody ServiceRequestDTO serviceRequestDTO) {

        ServiceResponseDTO response =
                serviceService.createService(serviceRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ETABLISSEMENT', 'ADMIN')")
    public ResponseEntity<ServiceResponseDTO> updateService(
            @RequestParam long id,
            @Valid @RequestBody ServiceRequestDTO serviceRequestDTO) {

        return ResponseEntity.ok(
                serviceService.updateService(id, serviceRequestDTO)
        );
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('ETABLISSEMENT', 'ADMIN')")
    public ResponseEntity<Void> deleteService(
            @RequestParam long id) {

        serviceService.delete(id);

        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>>getAllServices(){
        return ResponseEntity.ok(serviceService.getALlServices());
    }

}







