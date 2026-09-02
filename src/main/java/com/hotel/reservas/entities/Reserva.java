package com.hotel.reservas.entities;

import com.hotel.reservas.enums.TipoDeQuarto;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O nome do hóspede é obrigatório")
    @Column(nullable = false)
    private String nomeHospede;

    @NotNull(message = "O tipo de quarto é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDeQuarto tipoDeQuarto;

    @Min(value = 1, message = "O número de dia deve ser no mínimo 1")
    @Column(nullable = false)
    private int diasDeEstadia;

    @NotNull(message = "O valor da diária é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor da diária deve ser maior que 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDiaria;

    public Reserva() {}

    public Reserva(String nomeHospede, TipoDeQuarto tipoDeQuarto, int diasDeEstadia, BigDecimal valorDiaria) {
        this.nomeHospede = nomeHospede;
        this.tipoDeQuarto = tipoDeQuarto;
        this.diasDeEstadia = diasDeEstadia;
        this.valorDiaria = valorDiaria;
    }

    public BigDecimal calcularValorTotal() {
        return valorDiaria.multiply(new BigDecimal(diasDeEstadia));
    }

    public UUID getId() {
        return id;
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

    public void setTipoDeQuarto(TipoDeQuarto tipoDeQuarto) {
        this.tipoDeQuarto = tipoDeQuarto;
    }

    public int getDiasDeEstadia() {
        return diasDeEstadia;
    }

    public void setDiasDeEstadia(int diasDeEstadia) {
        this.diasDeEstadia = diasDeEstadia;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(BigDecimal valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "ID =" + id +
                ", Nome do Hospede ='" + nomeHospede + '\'' +
                ", Tipo de Quarto ='" + tipoDeQuarto + '\'' +
                ", Tempo de Estadia =" + diasDeEstadia + "dias" +
                ", Valor da Diária =" + valorDiaria +
                ", Valor Total =" + calcularValorTotal() +
                '}';
    }
}
