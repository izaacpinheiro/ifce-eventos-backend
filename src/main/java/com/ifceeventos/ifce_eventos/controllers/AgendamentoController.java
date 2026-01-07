package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoRequestDTO;
import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoResponseDTO;
import com.ifceeventos.ifce_eventos.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    // apenas para admin
    @PostMapping
    public ResponseEntity<Agendamento> create(@RequestBody AgendamentoRequestDTO body) {
        Agendamento novoAgendamento = this.agendamentoService.createAgendamento(body);
        return ResponseEntity.ok(novoAgendamento);
    }

    @GetMapping("/listar")
    public List<AgendamentoResponseDTO> listarAgendamentos() {
        return agendamentoService.listarAgendamentos().stream()
                .map(AgendamentoResponseDTO::new)
                .toList();
    }
}
