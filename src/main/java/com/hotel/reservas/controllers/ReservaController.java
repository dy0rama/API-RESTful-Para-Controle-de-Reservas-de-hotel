package com.hotel.reservas.controllers;

import com.hotel.reservas.dto.ReservaRequestDTO;
import com.hotel.reservas.dto.ReservaResponseDTO;
import com.hotel.reservas.services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/cadastro_de_hospede")
    public ResponseEntity<ReservaResponseDTO> criar(@Valid @RequestBody ReservaRequestDTO request) {
        ReservaResponseDTO response = reservaService.criarReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/lista_de_reservas")
    public ResponseEntity<List<ReservaResponseDTO>> listarOrdenado() {
        List<ReservaResponseDTO> reservas = reservaService.listarReservasTempoDeEstadia();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/busca_de_hospede/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarPorId(@PathVariable UUID id) {
        ReservaResponseDTO response = reservaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("atualizar_reserva/{id}")
    public ResponseEntity<ReservaResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody ReservaRequestDTO request) {
        ReservaResponseDTO response = reservaService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("remover_reserva/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        reservaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
