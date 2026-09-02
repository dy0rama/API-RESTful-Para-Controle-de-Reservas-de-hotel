package com.hotel.reservas.configurations;

import com.hotel.reservas.exceptions.OperacaoNaoPermitidaException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("administradorsecurity")
public class AdministradorSecurity {
    public boolean somenteAdmin(Authentication authentication, String mensagem) {
        boolean ehAdmin = authentication.getAuthorities().stream().anyMatch(authority ->
                        "ROLE_ADMIN".equals(authority.getAuthority()));

        if (!ehAdmin) {
            throw new OperacaoNaoPermitidaException(mensagem);
        }

        return true;
    }
}
