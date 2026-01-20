package com.ifceeventos.ifce_eventos.domain.agendamento;

import com.ifceeventos.ifce_eventos.domain.evento.EventoResumoDTO;
import com.ifceeventos.ifce_eventos.domain.lugar.LugarResumoDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record AgendamentoResponseDTO(
        UUID id,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        EventoResumoDTO evento,
        LugarResumoDTO lugar
) {

    public AgendamentoResponseDTO(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getData(),
                agendamento.getHoraInicio(),
                agendamento.getHoraFim(),
                new EventoResumoDTO(agendamento.getEvento()),
                new LugarResumoDTO(agendamento.getLugar())
        );
    }
}
