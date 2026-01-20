package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoResponseDTO;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.repositories.InscricaoRepository;
import com.ifceeventos.ifce_eventos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private UsuarioRepository repository;

    // lista dos os agendamentos do usuário
    public List<AgendamentoResponseDTO> listarAgendamentos(Usuario usuario) {
        List<Agendamento> agendamentos = inscricaoRepository.findAgendamentosByUsuarioId(usuario.getId());

        return agendamentos.stream()
                .map(AgendamentoResponseDTO::new)
                .toList();
    }

    public Usuario getUsuarioInfos(String email) {
        return repository.findByEmail(email);
    }
}
