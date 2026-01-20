package com.ifceeventos.ifce_eventos.domain.lugar;

public record LugarResumoDTO(String nome) {
    public LugarResumoDTO(Lugar lugar) {
        this(lugar.getNome());
    }
}
