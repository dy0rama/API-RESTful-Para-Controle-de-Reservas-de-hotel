package com.hotel.reservas.controllers;

import com.hotel.reservas.dto.*;
import com.hotel.reservas.entities.Usuario;
import com.hotel.reservas.mapper.UsuarioMapper;
import com.hotel.reservas.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuários", description = "Operações de gerenciamento dos usuários do sistema")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @Operation(summary = "Cadastrar usuário",
            description = """
                Cadastra um novo usuário no sistema.

                A operação requer autenticação e permissão de administrador.
                O e-mail deve ser único no sistema.
                """)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão de administrador"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")})
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody CriarUsuarioDTO request) {
        Usuario usuario = usuarioService.cadastrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDTO(usuario));
    }

    @Operation(summary = "Remover usuários",
            description = "Remover um usuário cadastrado no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar o recurso")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id, Authentication authentication) {
        usuarioService.removerUsuario(id,  authentication);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar usuários",
            description = "Retorna todos os usuários cadastrados no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuários listados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar o recurso")})
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios.stream().map(usuarioMapper::toDTO).toList());
    }

    @Operation(summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")})
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toDTO(usuario));
    }

    @Operation(summary = "Atualizar dados de usuários",
            description = "Atualizar dados de usuários cadastrados no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados enviados são inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar o recurso")})
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody UsuarioRequestDTO request) {
        Usuario usuario = usuarioService.atualizar(id, request);
        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));
    }

    @Operation(summary = "Alterar senha de um usuário",
            description = "Alterar senha de usuários cadastrados no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Senha atual é inválida"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar o recurso")})
    @PatchMapping("/{id}/alterar-senha")
    public ResponseEntity<Void> alterarSenha(@PathVariable UUID id, @Valid @RequestBody AlterarSenhaDTO request) {
        usuarioService.alterarSenha(id, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Resetar senha de um usuário",
            description = "Resetar senha de usuários cadastrados no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar o recurso")})
    @PatchMapping("/{id}/resetar-senha")
    public ResponseEntity<Void> resetarSenha(@PathVariable UUID id, @Valid @RequestBody ResetarSenhaDTO request) {
        usuarioService.resetarSenha(id, request);
        return ResponseEntity.ok().build();
    }
}
