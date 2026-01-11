package com.ifceeventos.ifce_eventos.repositories;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.inscricao.Inscricao;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InscricaoRepository extends JpaRepository<Inscricao, UUID> {

    boolean existsByUsuarioAndAgendamento(Usuario usuario, Agendamento agendamento);

    int countByAgendamento(Agendamento agendamento);

    @Query("""
        SELECT i.agendamento
        FROM Inscricao i
        WHERE i.usuario = :usuario
        ORDER BY i.agendamento.data, i.agendamento.horaInicio
    """)
    List<Agendamento> findAgendamentos(
            @Param("usuario") Usuario usuario
    );
}
