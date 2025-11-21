package com.ifceeventos.ifce_eventos.domain.usuario;

public enum TipoUsuario {
    ADMIN("admin"),
    PROFESSOR("professor"),
    PARTICIPANTE("participante");

    private String tipoUsuario;

    // construtor
    TipoUsuario(String tipoUsuario){
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoUsuario(){
        return tipoUsuario;
    }
}
