package com.ifceeventos.ifce_eventos.domain.lugar;

import java.util.UUID;

public record LugarResponseDTO(
        UUID id,
        String nome,
        Integer capacidade,
        String localizacao,
        String tipo
    ) {

    public LugarResponseDTO(Lugar lugar) {
        this(
            lugar.getId(),
            lugar.getNome(),
            lugar.getCapacidade(),
            lugar.getLocalizacao(),
            lugar.getTipo().name()
        );
    }
}
