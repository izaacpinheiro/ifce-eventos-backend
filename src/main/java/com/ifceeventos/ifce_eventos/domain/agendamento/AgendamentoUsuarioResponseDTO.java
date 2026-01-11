package com.ifceeventos.ifce_eventos.domain.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

// usado para retonar os agendamentos de um usuário
public record AgendamentoUsuarioResponseDTO(
        UUID idAgendamento,
        String tituloEvento,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        String local
    ) {

    public AgendamentoUsuarioResponseDTO(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getEvento().getTitulo(),
                agendamento.getData(),
                agendamento.getHoraInicio(),
                agendamento.getHoraFim(),
                agendamento.getLugar().getNome()
        );
    }
}
