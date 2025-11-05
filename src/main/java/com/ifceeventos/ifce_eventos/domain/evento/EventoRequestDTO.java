package com.ifceeventos.ifce_eventos.domain.evento;

import java.util.UUID;

public record EventoRequestDTO(
        // vai ser usado no create do Evento
        String titulo,
        String descricao,
        Boolean remote
        //UUID id_criador -> como vou salvar isso no bd?
) { }
