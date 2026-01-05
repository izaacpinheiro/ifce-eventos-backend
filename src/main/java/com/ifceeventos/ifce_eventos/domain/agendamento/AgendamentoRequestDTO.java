package com.ifceeventos.ifce_eventos.domain.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AgendamentoRequestDTO(
        // vai ser usado na criação dos Agendamentos
        UUID eventoId,
        UUID lugarId,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim
){ }
