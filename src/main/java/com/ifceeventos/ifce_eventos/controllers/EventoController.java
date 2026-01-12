package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.evento.EventoRequestDTO;
import com.ifceeventos.ifce_eventos.domain.evento.EventoResponseDTO;
import com.ifceeventos.ifce_eventos.infra.security.SecurityConfig;
import com.ifceeventos.ifce_eventos.services.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/evento") // endpoint
@Tag(name = "eventos", description = "controlador para manipulação dos eventos")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // apenas para admin e professor
    @PostMapping
    @Operation(summary = "Cria e salva eventos", description = "Método para criar e salvar eventos")
    @ApiResponse(responseCode = "200", description = "Evento criado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Evento> create(@RequestBody EventoRequestDTO body) {
        Evento novoEvento = this.eventoService.createEvento(body);
        return ResponseEntity.ok(novoEvento);
    }

    // apenas para admin
    @GetMapping("/pendentes")
    @Operation(summary = "Retorna os eventos pendentes", description = "Método para retornar os eventos pendentes")
    @ApiResponse(responseCode = "200", description = "Eventos retornados com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public List<EventoResponseDTO> listarPendentes() {
        return eventoService.listarEventosPendentes().stream()
                .map(EventoResponseDTO::new)
                .toList();
    }

    // apenas para admin
    @GetMapping("/aprovados")
    @Operation(summary = "Retorna os eventos aprovados", description = "Método para retornar os eventos aprovados")
    @ApiResponse(responseCode = "200", description = "Eventos retornados com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public List<EventoResponseDTO> listarEventosAprovados() {
        return eventoService.listarEventosAprovados().stream()
                .map(EventoResponseDTO::new)
                .toList();
    }

    // apenas para admin
    @PutMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar evento", description = "Método para aprovar evento pelo ID")
    @ApiResponse(responseCode = "200", description = "Evento aprovado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<EventoResponseDTO> aprovar(@PathVariable UUID id) {
        Evento evento = eventoService.aprovarEvento(id);
        return ResponseEntity.ok(new EventoResponseDTO(evento));
    }

    // apenas para admin
    @PutMapping("/{id}/recusar")
    @Operation(summary = "Recusar evento", description = "Método para recusar evento pelo ID")
    @ApiResponse(responseCode = "200", description = "Evento recusado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<EventoResponseDTO> recusar(@PathVariable UUID id) {
        Evento evento = eventoService.recusarEvento(id);
        return ResponseEntity.ok(new EventoResponseDTO(evento));
    }

    // apenas para admin
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar evento", description = "Método para cancelar evento pelo ID")
    @ApiResponse(responseCode = "200", description = "Evento cancelado com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<EventoResponseDTO> cancelar(@PathVariable UUID id) {
        Evento evento = eventoService.cancelarEvento(id);
        return ResponseEntity.ok(new EventoResponseDTO(evento));
    }
}
