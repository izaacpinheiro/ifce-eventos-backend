package com.ifceeventos.ifce_eventos.domain.lugar;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "lugar")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Lugar {
    @Id
    @GeneratedValue
    private UUID id;

    private String nome;
    private int capacidade;
    private String localizacao;

    @Enumerated(EnumType.STRING)
    private TipoLugar tipo;
}
