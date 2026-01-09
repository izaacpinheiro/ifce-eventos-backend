package com.ifceeventos.ifce_eventos.repositories;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.inscricao.Inscricao;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InscricaoRepository extends JpaRepository<Inscricao, UUID> {

    boolean existsByUsuarioAndAgendamento(Usuario usuario, Agendamento agendamento);

    int countByAgendamento(Agendamento agendamento);
}
