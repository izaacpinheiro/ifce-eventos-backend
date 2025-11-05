package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.evento.EventoRequestDTO;
import com.ifceeventos.ifce_eventos.domain.evento.StatusEvento;
import com.ifceeventos.ifce_eventos.repositories.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    public Evento createEvento(EventoRequestDTO data) {
        // cria um novo evento com as informações passadas pelo usuário
        Evento novoEvento = new Evento();
        novoEvento.setTitulo(data.titulo());
        novoEvento.setDescricao(data.descricao());
        novoEvento.setRemote(data.remote());
        novoEvento.setStatusAprovacao(StatusEvento.PENDENTE); // o evento é por padrão criado como pendente

        // Salvando o novo evento no bd
        repository.save(novoEvento);

        return novoEvento;
    }
}
