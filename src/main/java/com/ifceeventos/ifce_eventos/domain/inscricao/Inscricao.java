package com.ifceeventos.ifce_eventos.domain.inscricao;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "inscricao")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Inscricao {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatusInscricao status;

    // Relação N:1 com Agendamento
    @ManyToOne
    @JoinColumn(name = "id_agendamento")
    private Agendamento agendamento;

    // Relação N:1 com Usuario (um usuário poder ter várias inscricoes)
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
