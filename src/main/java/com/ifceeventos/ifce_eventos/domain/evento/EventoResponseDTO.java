package com.ifceeventos.ifce_eventos.domain.evento;

import java.util.UUID;

public record EventoResponseDTO (UUID id, String titulo, String descricao, String statusAprovacao, Boolean remote, UUID IdCriador, String nomeCriador) {

    public EventoResponseDTO(Evento evento) {
        this(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getStatusAprovacao().name(),
                evento.getRemote(),
                evento.getCriador() != null ? evento.getCriador().getId() : null,
                evento.getCriador() != null ? evento.getCriador().getNome() : null
        );
    }
}
