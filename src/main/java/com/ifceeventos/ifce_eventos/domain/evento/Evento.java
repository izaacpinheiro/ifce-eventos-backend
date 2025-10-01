package com.ifceeventos.ifce_eventos.domain.evento;

import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "evento")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    @Id
    @GeneratedValue
    private UUID id;

    private String titulo;
    private String descricao;
    private Boolean remote;

    @Enumerated(EnumType.STRING)
    private StatusEvento statusEvento;

    // Relacionamento N:1 com Usuario
    @ManyToOne
    @JoinColumn(name = "id_criador")
    private Usuario usuario;
}
