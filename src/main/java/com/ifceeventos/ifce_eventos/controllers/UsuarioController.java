package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoUsuarioResponseDTO;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me/agendamentos")
    public List<AgendamentoUsuarioResponseDTO> listarMeusAgendamentos(Authentication authentication) {
        // puxando o usuário a partir do JWT
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // retonna uma lista dos agendamentos do usuário
        return usuarioService.listarAgendamentos(usuario).stream()
                .map(AgendamentoUsuarioResponseDTO::new)
                .toList();
    }
}
