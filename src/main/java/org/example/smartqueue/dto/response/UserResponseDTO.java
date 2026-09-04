package org.example.smartqueue.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.example.smartqueue.enums.Role;
@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Role role;
}
