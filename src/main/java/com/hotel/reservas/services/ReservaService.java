package com.hotel.reservas.services;

import com.hotel.reservas.dto.ReservaRequestDTO;
import com.hotel.reservas.dto.ReservaResponseDTO;
import com.hotel.reservas.entities.Reserva;
import com.hotel.reservas.exceptions.ReservaNaoEncontradaException;
import com.hotel.reservas.mapper.ReservaMapper;
import com.hotel.reservas.repositories.ReservaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    public ReservaService(ReservaRepository reservaRepository, ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem criar reservas.'
    )""")
    public ReservaResponseDTO criarReserva(ReservaRequestDTO request) {
        Reserva reserva = reservaMapper.toEntity(request);

        Reserva reservaSalva = reservaRepository.save(reserva);

        return reservaMapper.toResponseDTO(reservaSalva);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ReservaResponseDTO buscarPorId(UUID id) {
        Reserva reserva = buscarEntidadePorId(id);
        return reservaMapper.toResponseDTO(reserva);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<ReservaResponseDTO> listarReservasTempoDeEstadia() {
        return reservaRepository.findAllByOrderByDiasDeEstadiaDesc().stream()
                .map(reservaMapper::toResponseDTO).toList();
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem atualizar reservas.'
    )""")
    public ReservaResponseDTO atualizar(UUID id, ReservaRequestDTO request) {
        Reserva reserva = buscarEntidadePorId(id);

        reservaMapper.updateEntity(reserva, request);

        Reserva reservaAtualizada = reservaRepository.save(reserva);

        return reservaMapper.toResponseDTO(reservaAtualizada);
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem remover reservas.'
    )""")
    public void deletar(UUID id) {
        Reserva reserva = buscarEntidadePorId(id);
        reservaRepository.delete(reserva);
    }

    private Reserva buscarEntidadePorId(UUID id) {
        return reservaRepository.findById(id).orElseThrow(() -> new ReservaNaoEncontradaException(id));
    }
}
