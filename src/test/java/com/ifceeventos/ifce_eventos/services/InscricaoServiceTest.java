package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.inscricao.Inscricao;
import com.ifceeventos.ifce_eventos.domain.lugar.Lugar;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.repositories.AgendamentoRepository;
import com.ifceeventos.ifce_eventos.repositories.InscricaoRepository;
import com.ifceeventos.ifce_eventos.repositories.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InscricaoServiceTest {

    @InjectMocks
    private InscricaoService inscricaoService;

    @Mock
    private InscricaoRepository inscricaoRepository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve inscrever o usuário no agendamento com sucesso")
    void deveInscreverUsuarioComSucesso() {
        // arrange
        UUID agendamentoId = UUID.randomUUID();
        String email = "usuario@email.com";

        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setEmail(email);

        Lugar lugar = new Lugar();
        lugar.setCapacidade(10);

        Agendamento agendamento = new Agendamento();
        agendamento.setId(agendamentoId);
        agendamento.setLugar(lugar);

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);

        when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.of(agendamento));

        when(inscricaoRepository.existsByUsuarioAndAgendamento(usuario, agendamento)).thenReturn(false);

        when(inscricaoRepository.countByAgendamento(agendamento)).thenReturn(3); // ainda há vagas

        inscricaoService.inscrever(agendamentoId, email);

        verify(inscricaoRepository).save(any(Inscricao.class));
    }
}