package com.ifceeventos.ifce_eventos.domain.inscricao;

import java.util.UUID;

// para fazer a inscrição so basta passa o ID do agendamento, as outras informções saem do JWT
public record InscricaoRequestDTO(UUID agendamentoId) { }
