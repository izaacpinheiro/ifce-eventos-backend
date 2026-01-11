package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.repositories.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    // lista dos os agendamentos do usuário
    public List<Agendamento> listarAgendamentos(Usuario usuario) {
        return inscricaoRepository.findAgendamentos(usuario);
    }
}
