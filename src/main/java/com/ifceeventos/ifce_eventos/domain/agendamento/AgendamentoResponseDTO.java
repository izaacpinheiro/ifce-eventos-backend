package com.ifceeventos.ifce_eventos.domain.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AgendamentoResponseDTO(
        UUID id,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        UUID idEvento,
        UUID idLugar) {

    public AgendamentoResponseDTO(Agendamento agendamento) {
        this(
            agendamento.getId(),
            agendamento.getData(),
            agendamento.getHoraInicio(),
            agendamento.getHoraFim(),
            agendamento.getEvento().getId(),
            agendamento.getLugar().getId()
        );
    }
}
