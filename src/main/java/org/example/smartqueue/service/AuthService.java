package org.example.smartqueue.service;

import lombok.RequiredArgsConstructor;
import org.example.smartqueue.dto.request.EtablissementRequestDTO;
import org.example.smartqueue.dto.request.LoginRequestDTO;
import org.example.smartqueue.dto.request.RegisterRequestDTO;
import org.example.smartqueue.dto.response.AuthResponseDTO;
import org.example.smartqueue.entity.Client;
import org.example.smartqueue.entity.Etablissement;
import org.example.smartqueue.enums.Role;
import org.example.smartqueue.mapper.ClientMapper;
import org.example.smartqueue.mapper.EtablissementMapper;
import org.example.smartqueue.repository.ClientRepository;
import org.example.smartqueue.repository.EtablissementRepository;
import org.example.smartqueue.repository.UserRepository;
import org.example.smartqueue.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final EtablissementRepository etablissementRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ClientMapper clientMapper;
    private final EtablissementMapper etablissementMapper;

    @Transactional
    public AuthResponseDTO registerClient(RegisterRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé.");
        }

        Client client = clientMapper.toEntity(requestDTO);
        client.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        client.setRole(Role.CLIENT);

        clientRepository.save(client);

        return login(new LoginRequestDTO(requestDTO.getEmail(), requestDTO.getPassword()));
    }

    @Transactional
    public void createEtablissementUser(EtablissementRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé.");
        }

        Etablissement etablissement = etablissementMapper.toEntity(requestDTO);
        etablissement.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        etablissement.setRole(Role.ETABLISSEMENT);

        etablissementRepository.save(etablissement);
    }

    public AuthResponseDTO login(LoginRequestDTO requestDTO) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(),
                        requestDTO.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return new AuthResponseDTO(token);
    }
}