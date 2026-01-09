package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.inscricao.Inscricao;
import com.ifceeventos.ifce_eventos.domain.inscricao.StatusInscricao;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.repositories.AgendamentoRepository;
import com.ifceeventos.ifce_eventos.repositories.InscricaoRepository;
import com.ifceeventos.ifce_eventos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void inscrever(UUID agendamentoId, String emailUsuario) {
        // busca o usuário
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario);

        // busca agendamento
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        // verifica se o usuário já está escrito
        boolean jaInscrito = inscricaoRepository.existsByUsuarioAndAgendamento(usuario, agendamento);

        if (jaInscrito) {
            throw new RuntimeException("Usuário já está inscrito nesse agendamento");
        }

        // verifica se há capacidade no local do evento
        int inscritos = inscricaoRepository.countByAgendamento(agendamento);
        int capacidade = agendamento.getLugar().getCapacidade();

        if (inscritos >= capacidade) {
            throw new RuntimeException("Vagas esgotadas");
        }

        // cria a inscrição
        Inscricao novaInscricao = new Inscricao();
        novaInscricao.setUsuario(usuario);
        novaInscricao.setAgendamento(agendamento);
        novaInscricao.setData(LocalDate.now());
        novaInscricao.setStatus(StatusInscricao.CONFIRMADA); // confirmada por padrão

        inscricaoRepository.save(novaInscricao);
    }
}
