package com.hotel.reservas.mapper;

import com.hotel.reservas.dto.CriarUsuarioDTO;
import com.hotel.reservas.dto.UsuarioRequestDTO;
import com.hotel.reservas.dto.UsuarioResponseDTO;
import com.hotel.reservas.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario toEntity(CriarUsuarioDTO request){
        return new Usuario(request.getEmail(), request.getSenha(), null);
    }

    public UsuarioResponseDTO toDTO(Usuario entity){
        return new UsuarioResponseDTO(entity.getId(), entity.getEmail(), entity.getRole());
    }

    public void updateEntity(Usuario usuario, UsuarioRequestDTO request){
        usuario.setEmail(request.getEmail());
    }
}
