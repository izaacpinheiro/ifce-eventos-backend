package com.ifceeventos.ifce_eventos.domain.agendamento;

import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.lugar.Lugar;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Table(name = "agendamento")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {
    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate date;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    // Relacionamento 1:1 com Evento
    @OneToOne
    @JoinColumn(name = "id_evento")
    private Evento evento;

    // Relacionamento N:1 com Local
    @ManyToOne
    @JoinColumn(name = "id_lugar")
    private Lugar lugar;
}
