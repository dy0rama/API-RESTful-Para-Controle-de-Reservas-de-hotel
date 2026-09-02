package com.hotel.reservas.dto;

import com.hotel.reservas.enums.TipoDeQuarto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ReservaRequestDTO {
    @NotBlank(message = "O nome do hóspede é obrigatório")
    private String nomeHospede;

    @NotNull(message = "O tipo do quarto é obrigatório")
    private TipoDeQuarto tipoDeQuarto;

    @Min(value = 1, message = "O número de dias deve ser no mínimo 1")
    private int diasDeEstadia;

    @NotNull(message = "O valor da diária é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor da diária deve ser maior que zero")
    private BigDecimal valorDiaria;

    public ReservaRequestDTO() {
    }

    public String getNomeHospede() {
        return nomeHospede;
    }

    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public TipoDeQuarto getTipoDeQuarto() {
        return tipoDeQuarto;
    }

    public void setTipoQuarto(TipoDeQuarto tipoQuarto) {
        this.tipoDeQuarto = tipoQuarto;
    }

    public int getDiasDeEstadia() {
        return diasDeEstadia;
    }

    public void setDiasDeEstadia(int numeroDias) {
        this.diasDeEstadia = numeroDias;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(BigDecimal valorDiaria) {
        this.valorDiaria = valorDiaria;
    }
}
