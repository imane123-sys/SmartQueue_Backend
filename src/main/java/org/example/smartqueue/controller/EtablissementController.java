package org.example.smartqueue.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.response.EtablissementResponseDTO;
import org.example.smartqueue.service.EtablissementService;
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
@RequestMapping("/api/etablissements")
public class EtablissementController {

    private final EtablissementService etablissementService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EtablissementResponseDTO> createEtablissement(
            @Valid @RequestBody EtablissementRequestDTO etablissementRequestDTO) {

        EtablissementResponseDTO response =
                etablissementService.createEtablissement(etablissementRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtablissementResponseDTO> getEtablissementById(
            @PathVariable long id) {

        return ResponseEntity.ok(
                etablissementService.getEtablissementById(id)
        );
    }

    @GetMapping("/proches")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    public ResponseEntity<List<EtablissementResponseDTO>> findEtablissementsProchesParServices(
            @RequestParam String serviceNom,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double rayonKm) {

        return ResponseEntity.ok(
                etablissementService.findEtablissementsProchesParServices(
                        serviceNom,
                        latitude,
                        longitude,
                        rayonKm
                )
        );
    }
    @GetMapping
    public ResponseEntity<Page<EtablissementResponseDTO>> getAllEtablissements(@PageableDefault(page=0 , size=10 , sort="nom",direction= Sort.Direction.ASC)Pageable pageable) {

        return ResponseEntity.ok(
                etablissementService.getAllEtablissements(pageable));

    }
// corriger probleme de modification
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ETABLISSEMENT', 'ADMIN')")
    public ResponseEntity<EtablissementResponseDTO> updateEtablissement(
            @PathVariable long id,
            @Valid @RequestBody EtablissementRequestDTO dto) {

        EtablissementResponseDTO updated =
                etablissementService.updateEtablissement(id, dto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteEtablissement(
            @RequestParam long id) {

        etablissementService.deleteEtablissement(id);

        return ResponseEntity.noContent().build();
    }
}