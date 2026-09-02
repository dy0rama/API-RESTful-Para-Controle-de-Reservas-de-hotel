package com.hotel.reservas.dto;

import com.hotel.reservas.enums.Role;

import java.util.UUID;

public class UsuarioResponseDTO {
    private final UUID id;
    private final String email;
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
