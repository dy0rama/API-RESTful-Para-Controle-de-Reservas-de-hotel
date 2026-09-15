package com.hotel.reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CriarUsuarioDTO {
    @Schema(description = "E-mail do usuário", example = "usuario@hotel.com")
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um e-mail válido")
    private String email;

    @Schema(description = "Senha atual utilizada pelo usuário para autenticação", example = "123456")
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve possuir pelo menos 6 caracteres")
    private String senha;

    public CriarUsuarioDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
