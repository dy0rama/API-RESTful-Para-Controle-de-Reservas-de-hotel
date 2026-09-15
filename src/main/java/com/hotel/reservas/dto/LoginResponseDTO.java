package com.hotel.reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponseDTO(
        @Schema(description = "Token JWT utilizado para autenticar as requisições protegidas", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token) {}
