package com.hotel.reservas.enums;

import io.swagger.v3.oas.annotations.media.Schema;

public enum TipoDeQuarto {
    @Schema(description = "Suíte padrão")
    STANDARD,
    @Schema(description = "Suíte de luxo")
    LUXO,
    @Schema(description = "Suíte presidencial")
    PRESIDENCIAL
}
