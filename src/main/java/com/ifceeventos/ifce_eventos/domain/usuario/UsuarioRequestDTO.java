package com.ifceeventos.ifce_eventos.domain.usuario;

public record UsuarioRequestDTO(
        // vai ser usado no create do Usuario
        String nome,
        String email,
        String senha
) { }
