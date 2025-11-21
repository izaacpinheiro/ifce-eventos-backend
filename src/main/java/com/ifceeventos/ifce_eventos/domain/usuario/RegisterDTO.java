package com.ifceeventos.ifce_eventos.domain.usuario;

public record RegisterDTO(String email, String senha, TipoUsuario role) {
}
