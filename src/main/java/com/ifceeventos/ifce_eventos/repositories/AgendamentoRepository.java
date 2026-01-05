package com.ifceeventos.ifce_eventos.repositories;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
    // query para verificação de conflito de agendamentos
    @Query("""
        SELECT a FROM Agendamento a
        WHERE a.lugar.id = :lugarId
            AND a.data = :data
            AND a.horaInicio < :horaFim
            AND a.horaFim > :horaInicio
    """)
    Optional<Agendamento> existeConflito(
            UUID lugarId,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim
    );
}
