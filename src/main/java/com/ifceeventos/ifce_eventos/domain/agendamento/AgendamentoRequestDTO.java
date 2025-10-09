package com.ifceeventos.ifce_eventos.domain.agendamento;

import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoRequestDTO(
        // vai ser usado no create de Agendamento
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        Evento evento,
        Local local
){ }
