package com.hotel.reservas.mapper;

import com.hotel.reservas.dto.ReservaRequestDTO;
import com.hotel.reservas.dto.ReservaResponseDTO;
import com.hotel.reservas.entities.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {
    public Reserva toEntity(ReservaRequestDTO request) {
        return new Reserva(request.getNomeHospede(), request.getTipoDeQuarto(), request.getDiasDeEstadia(), request.getValorDiaria());
    }

    public ReservaResponseDTO toResponseDTO(Reserva reserva) {
        return new ReservaResponseDTO(reserva.getId(), reserva.getNomeHospede(), reserva.getTipoDeQuarto(),
                reserva.getDiasDeEstadia(), reserva.getValorDiaria(), reserva.calcularValorTotal());
    }

    public void updateEntity(Reserva reserva, ReservaRequestDTO request) {
        reserva.setNomeHospede(request.getNomeHospede());
        reserva.setTipoDeQuarto(request.getTipoDeQuarto());
        reserva.setDiasDeEstadia(request.getDiasDeEstadia());
        reserva.setValorDiaria(request.getValorDiaria());
    }
}
