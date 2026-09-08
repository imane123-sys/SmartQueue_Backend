package org.example.smartqueue.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.response.EtablissementResponseDTO;
import org.example.smartqueue.service.EtablissementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/etablissements")
public class EtablissementController {

    private final EtablissementService etablissementService;

    @PostMapping("/create")
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
    public ResponseEntity<List<EtablissementResponseDTO>> getAllEtablissements() {

        return ResponseEntity.ok(
                etablissementService.getAllEtablissements()
        );
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateEtablissement(   @RequestParam long id,
                                                       @Valid @RequestBody EtablissementRequestDTO etablissementRequestDTO)
    {

        etablissementService.updateEtablissement(id,etablissementRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEtablissement(
            @RequestParam long id) {

        etablissementService.deleteEtablissement(id);

        return ResponseEntity.noContent().build();
    }
}