package com.ifceeventos.ifce_eventos.repositories;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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

    // retorna apenas eventos que ainda vão acontecer
    @Query("""
        SELECT a FROM Agendamento a
        WHERE a.date > :hoje
        OR (a.date = :hoje AND a.horaFim > :agora)
    """)
    List<Agendamento> listarAgendamentosFuturos(@Param("hoje") LocalDate hoje, @Param("agora") LocalTime agora);

    void deleteByEvento(Evento evento);
}
