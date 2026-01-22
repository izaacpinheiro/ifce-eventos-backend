package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoRequestDTO;
import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoResponseDTO;
import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.evento.StatusEvento;
import com.ifceeventos.ifce_eventos.domain.lugar.Lugar;
import com.ifceeventos.ifce_eventos.repositories.AgendamentoRepository;
import com.ifceeventos.ifce_eventos.repositories.EventoRepository;
import com.ifceeventos.ifce_eventos.repositories.LugarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private LugarRepository lugarRepository;

    @Test
    @DisplayName("Deve retornar o agendamento criado com sucesso")
    void createAgendamentoComSucesso() {
        Evento evento = new Evento();
        evento.setStatusAprovacao(StatusEvento.APROVADO);
        evento.setDataPrevista(LocalDate.now());

        Lugar lugar = new Lugar();

        when(agendamentoRepository.existeConflito(any(), any(), any(), any())).thenReturn(Optional.empty());
        when(eventoRepository.findById(any())).thenReturn(Optional.of(evento));
        when(lugarRepository.findById(any())).thenReturn(Optional.of(lugar));
        when(agendamentoRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        AgendamentoRequestDTO request = new AgendamentoRequestDTO(UUID.randomUUID(), UUID.randomUUID(), null, LocalTime.of(10, 0), LocalTime.of(12,0));

        Agendamento agendamento = agendamentoService.createAgendamento(request);

        assertNotNull(agendamento);
        assertEquals(evento, agendamento.getEvento());
        assertEquals(lugar, agendamento.getLugar());
        assertEquals(LocalTime.of(10, 0), agendamento.getHoraInicio());
    }

    @Test
    @DisplayName("Deve retornar os agendamentos que ainda vão acontecer")
    void deveListarAgendamentosFuturos() {
        when(agendamentoRepository.listarAgendamentosFuturos(any(), any())).thenReturn(List.of(new Agendamento()));
        List<AgendamentoResponseDTO> eventos = agendamentoService.listarAgendamentos();
        assertEquals(1, eventos.size());
    }
}