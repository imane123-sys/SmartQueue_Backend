package org.example.smartqueue.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.ClientRequestDTO;
import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.request.LoginRequestDTO;
import org.example.smartqueue.dto.request.RegisterRequestDTO;
import org.example.smartqueue.dto.response.AuthResponseDTO;
import org.example.smartqueue.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/client")
    public ResponseEntity<AuthResponseDTO> registerClient(@Valid @RequestBody ClientRequestDTO requestDTO) {
        AuthResponseDTO response = authService.registerClient(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register/etablissement")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> createEtablissement(@Valid @RequestBody EtablissementRequestDTO requestDTO) {
        authService.createEtablissementUser(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Compte établissement créé avec succès.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        AuthResponseDTO response = authService.login(requestDTO);
        return ResponseEntity.ok(response);
    }
}