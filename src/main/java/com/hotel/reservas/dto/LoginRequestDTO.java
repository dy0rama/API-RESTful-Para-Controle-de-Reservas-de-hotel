package com.hotel.reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @Schema(description = "E-mail utilizado para autenticação", example = "admin@hotel.com")
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O e-mail informado é inválido.") String email,
        @Schema(description = "Senha utilizada para autenticação", example = "123456")
        @NotBlank(message = "A senha é obrigatória.") String senha) {}
