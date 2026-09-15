package com.hotel.reservas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetarSenhaDTO {
    @Schema(description = "Senha nova que será utilizada pelo usuário para autenticação", example = "123456")
    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 6, message = "A senha deve possuir pelo menos 6 caracteres")
    private String novaSenha;

    public ResetarSenhaDTO() {
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
