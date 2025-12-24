package com.ifceeventos.ifce_eventos.domain.evento;

public record EventoRequestDTO(
        // vai ser usado no create do Evento
        String titulo,
        String descricao,
        Boolean remote
) { }
