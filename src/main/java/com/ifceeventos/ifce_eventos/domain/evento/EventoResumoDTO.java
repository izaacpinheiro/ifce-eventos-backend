package com.ifceeventos.ifce_eventos.domain.evento;

public record EventoResumoDTO(String titulo, Boolean remote) {
    public EventoResumoDTO(Evento evento) {
        this(
                evento.getTitulo(),
                evento.getRemote()
        );
    }
}
