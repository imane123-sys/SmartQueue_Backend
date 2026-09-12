package org.example.smartqueue.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.smartqueue.enums.Role;

@Getter
@Setter
public class ClientRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @Email(message = "Format d'email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    private String telephone;
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
    private Role role;
}