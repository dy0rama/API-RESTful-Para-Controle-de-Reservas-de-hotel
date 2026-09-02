package com.hotel.reservas.controllers;

import com.hotel.reservas.dto.*;
import com.hotel.reservas.entities.Usuario;
import com.hotel.reservas.mapper.UsuarioMapper;
import com.hotel.reservas.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody CriarUsuarioDTO request) {
        Usuario usuario = usuarioService.cadastrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDTO(usuario));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id, Authentication authentication) {
        usuarioService.removerUsuario(id,  authentication);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios.stream().map(usuarioMapper::toDTO).toList());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioMapper.toDTO(usuario));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody UsuarioRequestDTO request) {
        Usuario usuario = usuarioService.atualizar(id, request);
        return ResponseEntity.ok(usuarioMapper.toDTO(usuario));
    }

    @PatchMapping("/senha/{id}")
    public ResponseEntity<Void> alterarSenha(@PathVariable UUID id, @Valid @RequestBody AlterarSenhaDTO request) {
        usuarioService.alterarSenha(id, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/resetar_senha/{id}")
    public ResponseEntity<Void> resetarSenha(@PathVariable UUID id, @Valid @RequestBody ResetarSenhaDTO request) {
        usuarioService.resetarSenha(id, request);
        return ResponseEntity.ok().build();
    }
}
