package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoResponseDTO;
import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.lugar.Lugar;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.repositories.InscricaoRepository;
import com.ifceeventos.ifce_eventos.repositories.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private InscricaoRepository inscricaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve listar os agendamentos do usuário com sucesso")
    void deveListarAgendamentosDoUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());

        Evento evento = new Evento();
        evento.setTitulo("SEINFO");
        evento.setRemote(false);

        Lugar lugar = new Lugar();
        lugar.setNome("Auditório");

        Agendamento agendamento = new Agendamento();
        agendamento.setEvento(evento);
        agendamento.setLugar(lugar);
        agendamento.setData(LocalDate.now());
        agendamento.setHoraInicio(LocalTime.of(16, 0));
        agendamento.setHoraFim(LocalTime.of(18, 0));

        when(inscricaoRepository.findAgendamentosByUsuarioId(usuario.getId())).thenReturn(List.of(agendamento));

        List<AgendamentoResponseDTO> result = usuarioService.listarAgendamentos(usuario);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("SEINFO", result.get(0).evento().titulo());
        assertEquals("Auditório", result.get(0).lugar().nome());
    }

    @Test
    @DisplayName("Deve buscar usuário pelo email")
    void deveBuscarUsuarioPeloEmail() {
        // arrange
        String email = "usuario@email.com";

        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);

        Usuario result = usuarioService.getUsuarioInfos(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }
}