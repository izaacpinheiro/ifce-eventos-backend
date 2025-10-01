package com.ifceeventos.ifce_eventos.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "usuario")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue
    private UUID id;

    private String nome;
    private String email;
    private String senha; // será armazenada criptografada

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    // Talves precise de uma relacionamento com Inscricao
}
