package org.example.smartqueue.service;

import org.example.smartqueue.dto.request.ClientRequestDTO;
import org.example.smartqueue.dto.request.LoginRequestDTO;
import org.example.smartqueue.dto.response.AuthResponseDTO;
import org.example.smartqueue.entity.Client;
import org.example.smartqueue.entity.User;
import org.example.smartqueue.enums.Role;
import org.example.smartqueue.mapper.ClientMapper;
import org.example.smartqueue.mapper.EtablissementMapper;
import org.example.smartqueue.repository.ClientRepository;
import org.example.smartqueue.repository.EtablissementRepository;
import org.example.smartqueue.repository.UserRepository;
import org.example.smartqueue.security.JwtService;
import org.example.smartqueue.service.imp.AuthServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private ClientRepository clientRepository;
    @Mock private EtablissementRepository etablissementRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtService jwtService;
    @Mock private ClientMapper clientMapper;
    @Mock private EtablissementMapper etablissementMapper;

    @InjectMocks
    private AuthServiceImp authService;

    // --- registerClient() Tests ---

    @Test
    @DisplayName("registerClient: should register new client and return token when email is not taken")
    void registerClient_WhenEmailNotExists_ShouldRegisterAndReturnToken() {
        ClientRequestDTO request = new ClientRequestDTO();
        request.setEmail("client@test.com");
        request.setPassword("password123");

        Client client = new Client();
        client.setEmail("client@test.com");

        Authentication authentication = mock(Authentication.class);
        when(userRepository.existsByEmail("client@test.com")).thenReturn(false);
        when(clientMapper.toEntity(request)).thenReturn(client);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(clientRepository.save(client)).thenReturn(client);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(client);
        when(jwtService.generateToken(client)).thenReturn("mocked-jwt-token");

        AuthResponseDTO response = authService.registerClient(request);

        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());
        assertEquals("encodedPassword", client.getPassword());
        assertEquals(Role.CLIENT, client.getRole());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    @DisplayName("registerClient: should throw IllegalArgumentException when email already exists")
    void registerClient_WhenEmailAlreadyExists_ShouldThrowIllegalArgumentException() {
        ClientRequestDTO request = new ClientRequestDTO();
        request.setEmail("existing@test.com");

        when(userRepository.existsByEmail("existing@test.com")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, 
            () -> authService.registerClient(request)
        );
        assertEquals("Cet email est déjà utilisé.", ex.getMessage());
        verify(clientRepository, never()).save(any());
    }

    // --- login() Tests ---

    @Test
    @DisplayName("login: should return token on valid credentials")
    void login_WhenCredentialsValid_ShouldReturnToken() {
        LoginRequestDTO request = new LoginRequestDTO("user@test.com", "password123");
        User user = new User();
        user.setEmail("user@test.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtService.generateToken(user)).thenReturn("generated-jwt");

        AuthResponseDTO response = authService.login(request);

        assertNotNull(response);
        assertEquals("generated-jwt", response.getToken());
    }

    @Test
    @DisplayName("login: should propagate exception on bad credentials")
    void login_WhenBadCredentials_ShouldThrowAuthenticationException() {
        LoginRequestDTO request = new LoginRequestDTO("user@test.com", "wrongPassword");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
        verify(jwtService, never()).generateToken(any());
    }
}
