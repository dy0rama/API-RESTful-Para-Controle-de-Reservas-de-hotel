package com.hotel.reservas.dto;

import com.hotel.reservas.enums.TipoDeQuarto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ReservaRequestDTO {
    @Schema(description = "Nome completo do hóspede", example = "João da Silva")
    @NotBlank(message = "O nome do hóspede é obrigatório")
    private String nomeHospede;

    @Schema(description = "Tipo do quarto reservado", example = "STANDARD")
    @NotNull(message = "O tipo do quarto é obrigatório")
    private TipoDeQuarto tipoDeQuarto;

    @Schema(description = "Quantidade de dias da reserva", example = "5", minimum = "1")
    @Min(value = 1, message = "O número de dias deve ser no mínimo 1")
    private int diasDeEstadia;

    @Schema(description = "Valor da diária", example = "250.00", minimum = "0.01")
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
