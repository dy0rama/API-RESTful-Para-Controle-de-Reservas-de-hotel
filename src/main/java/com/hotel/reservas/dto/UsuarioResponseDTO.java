package com.hotel.reservas.dto;

import com.hotel.reservas.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public class UsuarioResponseDTO {
    @Schema(description = "Identificador único do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
    private final UUID id;

    @Schema(description = "E-mail do usuário", example = "usuario@hotel.com")
    private final String email;

    @Schema(description = "Perfil de acesso do usuário", example = "USER")
    private final Role role;

    public UsuarioResponseDTO(UUID id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
