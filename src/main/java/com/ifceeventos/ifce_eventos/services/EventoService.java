package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.evento.EventoRequestDTO;
import com.ifceeventos.ifce_eventos.domain.evento.StatusEvento;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.repositories.AgendamentoRepository;
import com.ifceeventos.ifce_eventos.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Evento createEvento(EventoRequestDTO data) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // puxa o criador do evento
        Usuario criador = (Usuario) authentication.getPrincipal();

        // cria um novo evento com as informações passadas pelo usuário
        Evento novoEvento = new Evento();
        novoEvento.setTitulo(data.titulo());
        novoEvento.setDescricao(data.descricao());
        novoEvento.setRemote(data.remote());
        novoEvento.setDataPrevista(data.dataPrevista());
        novoEvento.setStatusAprovacao(StatusEvento.PENDENTE); // o evento é por padrão criado como pendente
        novoEvento.setCriador(criador); // criador do evento (salva apenas o ID)

        // Salvando o novo evento no bd
        repository.save(novoEvento);

        return novoEvento;
    }

    public List<Evento> listarEventosPendentes() {
        return repository.findByStatusAprovacao(StatusEvento.PENDENTE);
    }

    public List<Evento> listarEventosAprovados() {
        return repository.findByStatusAprovacao(StatusEvento.APROVADO);
    }

    public List<Evento> listarAprovadosSemAgendamento() {
        return repository.findAprovadosSemAgendamento();
    }

    public Evento aprovarEvento(UUID eventoId) {
        Evento evento = repository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        evento.setStatusAprovacao(StatusEvento.APROVADO);
        return repository.save(evento);
    }

    public Evento recusarEvento(UUID eventoId) {
        Evento evento = repository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        evento.setStatusAprovacao(StatusEvento.RECUSADO);
        return repository.save(evento);
    }

    public Evento cancelarEvento(UUID eventoId) {
        Evento evento = repository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        evento.setStatusAprovacao(StatusEvento.CANCELADO);

        // remove todos os agendamentos ligados a esse evento
        agendamentoRepository.deleteByEvento(evento);

        return repository.save(evento);
    }

}
