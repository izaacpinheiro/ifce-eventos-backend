package com.ifceeventos.ifce_eventos.domain.evento;

import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;

public record EventoRequestDTO(
        // vai ser usado no create do Evento
        String titulo,
        String descricao,
        Boolean remote,
        Usuario usuario
) { }
