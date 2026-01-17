package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoRequestDTO;
import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.evento.StatusEvento;
import com.ifceeventos.ifce_eventos.domain.lugar.Lugar;
import com.ifceeventos.ifce_eventos.repositories.AgendamentoRepository;
import com.ifceeventos.ifce_eventos.repositories.EventoRepository;
import com.ifceeventos.ifce_eventos.repositories.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private LugarRepository lugarRepository;

    @Transactional
    public Agendamento createAgendamento(AgendamentoRequestDTO data) {

        // verificação de horário
        if (data.horaFim().isBefore(data.horaInicio()) || data.horaFim().equals(data.horaInicio())) {
            throw new RuntimeException("Hora final deve ser depois da hora inicial");
        }

        // verifica se a conflito na crição do agendamento
        boolean conflito = agendamentoRepository.existeConflito(
                data.lugarId(),
                data.data(),
                data.horaInicio(),
                data.horaFim()
        ).isPresent();

        if (conflito) throw new RuntimeException("Já existe um evento nesse local e evento");

        Evento evento = eventoRepository.findById(data.eventoId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (evento.getStatusAprovacao() != StatusEvento.APROVADO)
            throw new RuntimeException("Somente eventos aprovados podem ser agendados");

        Lugar lugar = lugarRepository.findById(data.lugarId())
                .orElseThrow(() -> new RuntimeException("Lugar não encontrado"));

        LocalDate dataAgendamento;

        if (data.data() != null) {
            dataAgendamento = data.data();
        } else if (evento.getDataPrevista() != null) {
            dataAgendamento = evento.getDataPrevista();
        } else {
            throw new RuntimeException("Evento não possui data prevista");
        }

        // cria agendamento
        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setData(dataAgendamento);
        novoAgendamento.setHoraInicio(data.horaInicio());
        novoAgendamento.setHoraFim(data.horaFim());
        novoAgendamento.setEvento(evento);
        novoAgendamento.setLugar(lugar);

        agendamentoRepository.save(novoAgendamento);

        return novoAgendamento;
    }

    public List<Agendamento> listarAgendamentos() {
        return agendamentoRepository.listarAgendamentosFuturos(LocalDate.now(), LocalTime.now());
    }
}
