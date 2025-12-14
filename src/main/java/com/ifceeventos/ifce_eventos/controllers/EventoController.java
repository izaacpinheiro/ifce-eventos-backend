package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.evento.EventoRequestDTO;
import com.ifceeventos.ifce_eventos.domain.evento.EventoResponseDTO;
import com.ifceeventos.ifce_eventos.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/evento") // endpoint
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    public ResponseEntity<Evento> create(@RequestBody EventoRequestDTO body) {
        Evento novoEvento = this.eventoService.createEvento(body);
        return ResponseEntity.ok(novoEvento);
    }

    @GetMapping("/pendentes")
    public List<EventoResponseDTO> listarPendentes() {
        return eventoService.listarEventosPendentes().stream()
                .map(EventoResponseDTO::new)
                .toList();
    }
}
