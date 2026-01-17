package com.ifceeventos.ifce_eventos.repositories;

import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.evento.StatusEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EventoRepository extends JpaRepository<Evento, UUID> {

    List<Evento> findByStatusAprovacao(StatusEvento status);

    @Query("""
        SELECT e
        FROM Evento e
        WHERE e.statusAprovacao = 'APROVADO'
        AND e.id NOT IN (
            SELECT a.evento.id FROM Agendamento a
        )
    """)
    List<Evento> findAprovadosSemAgendamento();
}
