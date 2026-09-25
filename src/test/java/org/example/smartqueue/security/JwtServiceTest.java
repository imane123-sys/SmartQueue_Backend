package org.example.smartqueue.security;

import org.example.smartqueue.entity.User;
import org.example.smartqueue.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "jwtSecret", secretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 3600000);
    }


    @Test
    @DisplayName("validateToken: should return true for a valid generated token")
    void validateToken_WhenTokenValid_ShouldReturnTrue() {
        User user = new User();
        user.setId(3L);
        user.setEmail("test@domain.com");
        user.setRole(Role.ETABLISSEMENT);

        String token = jwtService.generateToken(user);

        assertTrue(jwtService.validateToken(token));
    }

    @Test
    @DisplayName("validateToken: should return false for malformed or tampered token")
    void validateToken_WhenTokenMalformed_ShouldReturnFalse() {
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.invalidpayload.signature";

        assertFalse(jwtService.validateToken(invalidToken));
        assertFalse(jwtService.validateToken(""));
    }
}
