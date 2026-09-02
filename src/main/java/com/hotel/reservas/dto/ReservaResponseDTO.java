package com.hotel.reservas.dto;

import com.hotel.reservas.enums.TipoDeQuarto;

import java.math.BigDecimal;
import java.util.UUID;

public class ReservaResponseDTO {
    private final UUID id;
    private final String nomeHospede;
    private final TipoDeQuarto tipoDeQuarto;
    private final int diasDeEstadia;
    private final BigDecimal valorDiaria;
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
