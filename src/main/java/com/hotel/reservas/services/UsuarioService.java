package com.hotel.reservas.services;

import com.hotel.reservas.dto.AlterarSenhaDTO;
import com.hotel.reservas.dto.CriarUsuarioDTO;
import com.hotel.reservas.dto.ResetarSenhaDTO;
import com.hotel.reservas.dto.UsuarioRequestDTO;
import com.hotel.reservas.entities.Usuario;
import com.hotel.reservas.enums.Role;
import com.hotel.reservas.exceptions.*;
import com.hotel.reservas.mapper.UsuarioMapper;
import com.hotel.reservas.repositories.UsuarioRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem criar novos usuários.'
    )""")
    public Usuario cadastrarUsuario(CriarUsuarioDTO request) {
        if (usuarioRepository.existsByEmail(request.getEmail()))
            throw new EmailJaCadastradoException();

        Usuario usuario = usuarioMapper.toEntity(request);

        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode("TEMP"));
        usuario.setRole(Role.USER);

        return usuarioRepository.save(usuario);
    }

    @PreAuthorize("@usuarioSecurity.verificarPodeAlterar(#id, authentication)")
    public Usuario atualizar(UUID id, UsuarioRequestDTO request) {
        Usuario usuario = buscarPorId(id);

        boolean emailJaPertenceAOutroUsuario = usuarioRepository.existsByEmailAndIdNot(request.getEmail(), id);

        if (emailJaPertenceAOutroUsuario) {
            throw new EmailJaCadastradoException();
        }

        usuarioMapper.updateEntity(usuario, request);

        return usuarioRepository.save(usuario);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @PreAuthorize("@usuarioSecurity.verificarPodeAlterar(#id, authentication)")
    public void alterarSenha(UUID id, AlterarSenhaDTO request) {
        Usuario usuario = buscarPorId(id);

        boolean senhaAtualCorreta = passwordEncoder.matches(request.getSenhaAtual(), usuario.getSenha());

        if (!senhaAtualCorreta) {
            throw new SenhaAtualIncorretaException();
        }

        usuario.setSenha(passwordEncoder.encode(request.getNovaSenha()));

        usuarioRepository.save(usuario);
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem resetar senhas de usuários.'
    )""")
    public void resetarSenha(UUID id, ResetarSenhaDTO request) {
        Usuario usuario = buscarPorId(id);

        String novaSenhaHash = passwordEncoder.encode(request.getNovaSenha());

        usuario.setSenha(novaSenhaHash);

        usuarioRepository.save(usuario);
    }

    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
    authentication,
    'Somente administradores podem remover usuários.'
    )""")
    public void removerUsuario(UUID idUsuario, Authentication authentication) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
                        new UsuarioNaoEncontradoException(idUsuario));

        String emailAutenticado = authentication.getName();

        if (usuario.getEmail().equalsIgnoreCase(emailAutenticado))
            throw new OperacaoNaoPermitidaException("O administrador não pode excluir a própria conta.");

        usuarioRepository.deleteById(idUsuario);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
}
