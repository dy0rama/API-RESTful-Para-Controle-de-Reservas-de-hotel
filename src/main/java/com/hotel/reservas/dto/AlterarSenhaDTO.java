package com.hotel.reservas.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AlterarSenhaDTO {
    @Schema(description = "Senha atual utilizada pelo usuário para autenticação", example = "123456")
    @NotBlank(message = "A senha atual é obrigatória")
    private String senhaAtual;

    @Schema(description = "Senha nova  que será utilizada pelo usuário para autenticação", example = "123456")
    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 6, message = "A senha deve possuir pelo menos 6 caracteres")
    private String novaSenha;

    public AlterarSenhaDTO() {
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
