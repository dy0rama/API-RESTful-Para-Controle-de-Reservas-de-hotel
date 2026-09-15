package com.hotel.reservas.controllers;

import com.hotel.reservas.dto.ReservaRequestDTO;
import com.hotel.reservas.dto.ReservaResponseDTO;
import com.hotel.reservas.services.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reservas")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Reservas", description = "Operações de gerenciamento de reservas")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Operation(summary = "Criar reserva",
            description = "Cria uma nova reserva. Operação permitida somente para administradores.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reserva criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão")})
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> criar(@Valid @RequestBody ReservaRequestDTO request) {
        ReservaResponseDTO response = reservaService.criarReserva(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar reservas",
            description = "Retorna todas as reservas ordenadas pelo número de dias em ordem decrescente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservas retornadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")})
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarOrdenado() {
        List<ReservaResponseDTO> reservas = reservaService.listarReservasTempoDeEstadia();
        return ResponseEntity.ok(reservas);
    }

    @Operation(summary = "Buscar reserva por ID",
            description = "Retorna uma reserva específica utilizando seu UUID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "400", description = "UUID inválido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")})
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarPorId(@PathVariable UUID id) {
        ReservaResponseDTO response = reservaService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar reserva",
            description = "Atualiza uma reserva existente. Operação permitida somente para administradores.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reserva atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")})
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody ReservaRequestDTO request) {
        ReservaResponseDTO response = reservaService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Excluir reserva",
            description = "Exclui uma reserva existente. Operação permitida somente para administradores.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Reserva excluída com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão"),
            @ApiResponse(responseCode = "404", description = "Reserva não encontrada")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        reservaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
