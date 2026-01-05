package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoRequestDTO;
import com.ifceeventos.ifce_eventos.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
