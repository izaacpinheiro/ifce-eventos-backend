package com.ifceeventos.ifce_eventos.domain.usuario;

import java.util.UUID;

public record UsuarioInfosDTO(
        UUID id,
        String nome,
        String email,
        String tipoUsuario
    ) {

    public UsuarioInfosDTO(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTipoUsuario().name()
        );
    }
}
