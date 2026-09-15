package com.hotel.reservas.dto;

import com.hotel.reservas.enums.TipoDeQuarto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public class ReservaResponseDTO {
    @Schema(description = "Identificador único do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
    private final UUID id;

    @Schema(description = "Nome completo do hóspede", example = "João da Silva")
    private final String nomeHospede;

    @Schema(description = "Tipo do quarto reservado", example = "STANDARD")
    private final TipoDeQuarto tipoDeQuarto;

    @Schema(description = "Quantidade de dias da reserva", example = "5", minimum = "1")
    private final int diasDeEstadia;

    @Schema(description = "Valor da diária", example = "250.00", minimum = "0.01")
    private final BigDecimal valorDiaria;

    @Schema(description = "Valor total da estadia", example = "3250.00", minimum = "0.01")
    private final BigDecimal valorTotal;

    public ReservaResponseDTO(UUID id, String nomeHospede, TipoDeQuarto tipoDeQuarto, int diasDeEstadia, BigDecimal valorDiaria, BigDecimal valorTotal) {
        this.id = id;
        this.nomeHospede = nomeHospede;
        this.tipoDeQuarto = tipoDeQuarto;
        this.diasDeEstadia = diasDeEstadia;
        this.valorDiaria = valorDiaria;
        this.valorTotal = valorTotal;
    }

    public UUID getId() {
        return id;
    }

    public String getNomeHospede() {
        return nomeHospede;
    }

    public TipoDeQuarto getTipoDeQuarto() {
        return tipoDeQuarto;
    }

    public int getDiasDeEstadia() {
        return diasDeEstadia;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
