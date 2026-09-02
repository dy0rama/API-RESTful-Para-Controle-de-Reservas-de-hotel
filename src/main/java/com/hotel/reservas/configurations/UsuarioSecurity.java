package com.hotel.reservas.configurations;

import com.hotel.reservas.exceptions.OperacaoNaoPermitidaException;
import com.hotel.reservas.repositories.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UsuarioSecurity {
    private final UsuarioRepository usuarioRepository;

    public UsuarioSecurity(
            UsuarioRepository usuarioRepository) {this.usuarioRepository = usuarioRepository;
    }

    public void verificarPodeAlterar(UUID id, Authentication authentication) {
        boolean ehAdmin = authentication.getAuthorities().stream().anyMatch(authority ->
                "ROLE_ADMIN".equals(authority.getAuthority()));

        if (ehAdmin) {
            return;
        }

        boolean ehProprioUsuario = usuarioRepository.findById(id).map(usuario ->
                        usuario.getEmail().equalsIgnoreCase(authentication.getName())).orElse(false);

        if (!ehProprioUsuario) {
            throw new OperacaoNaoPermitidaException("Você não pode alterar os dados de outro usuário.");
        }
    }
}