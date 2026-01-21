package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.evento.Evento;
import com.ifceeventos.ifce_eventos.domain.evento.EventoRequestDTO;
import com.ifceeventos.ifce_eventos.domain.evento.EventoResponseDTO;
import com.ifceeventos.ifce_eventos.infra.security.SecurityConfig;
import com.ifceeventos.ifce_eventos.services.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/evento") // endpoint
@Tag(name = "Eventos", description = "Endpoints responsáveis pelo gerenciamento dos eventos do sistema")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    @Operation(summary = "Cria novo evento", description = """
        Cria um novo evento no sistema.
        
        Regras:
        - Apenas usuários com perfil ADMIN ou PROFESSOR podem criar eventos
        - Todo evento criado inica com status PENDENTE
        - O evento precisar ser aprovado por um ADMIN para ser agendado
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<Evento> create(@RequestBody EventoRequestDTO body) {
        Evento novoEvento = this.eventoService.createEvento(body);
        return ResponseEntity.ok(novoEvento);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pesquisa evento por ID", description = """
        Retorna todas as informaçõs de um evento.
        - Disponível para todos os usuários do sistema
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento retornado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<EventoResponseDTO> buscarEventoPorId(@PathVariable UUID id) {
        Evento evento = eventoService.buscarEventoPorId(id);
        return ResponseEntity.ok(new EventoResponseDTO(evento));
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Listar eventos pendentes", description = """
        Retorna todos os eventos com status PENDENTE.
        
        Regras:
        - Disponível apenas para ADMIN
        - Eventos pendetes aguardam ser aprovados ou recusados
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Eventos retornados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<List<EventoResponseDTO>> listarPendentes() {
        List<EventoResponseDTO> eventosPendentes = this.eventoService.listarEventosPendentes();
        return ResponseEntity.ok(eventosPendentes);
    }

    @GetMapping("/aprovados")
    @Operation(summary = "Listar eventos aprovados", description = """
        Retorna todos os eventos com status APROVADO.
        
        Regras:
        - Disponível apenas para ADMIN
        - Eventos aprovados podem ser agendados
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Eventos retornados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<List<EventoResponseDTO>> listarEventosAprovados() {
        List<EventoResponseDTO> eventosAprovados = this.eventoService.listarEventosAprovados();
        return ResponseEntity.ok(eventosAprovados);
    }

    @GetMapping("/aprovados/sem-agendamento")
    @Operation(summary = "Listar eventos aprovados sem agendamento", description = """
        Retorna todos os eventos com status APROVADO que ainda não tem agendamento.
        
        Regras:
        - Disponível apenas para ADMIN
        - Útil para ADMINs quando forem fazer agendamentos
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Eventos retornados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<List<EventoResponseDTO>> listarAprovadosSemAgendamento() {
        List<EventoResponseDTO> eventosAprovadosSemAgendamento = this.eventoService.listarAprovadosSemAgendamento();
        return ResponseEntity.ok(eventosAprovadosSemAgendamento);
    }

    @PutMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar evento", description = """
        Aprova um evento pelo seu ID.
        
        Regras:
        - Disponível apenas para ADMIN
        - Evento precisar estar com status PENDENTE
        - Após aprovação, o evento pode ser agendado
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento aprovado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<EventoResponseDTO> aprovar(@PathVariable UUID id) {
        Evento evento = eventoService.aprovarEvento(id);
        return ResponseEntity.ok(new EventoResponseDTO(evento));
    }

    @PutMapping("/{id}/recusar")
    @Operation(summary = "Recusar evento", description = """
        Recusa um evento pelo seu ID.
        
        Regras:
        - Disponível apenas para ADMIN
        - Eventos recusados não foram aceitos para ser feito agendamento
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento recusado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<EventoResponseDTO> recusar(@PathVariable UUID id) {
        Evento evento = eventoService.recusarEvento(id);
        return ResponseEntity.ok(new EventoResponseDTO(evento));
    }

    // apenas para admin
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar evento", description = """
        Cancela um evento ja aprovado.
        
        Regras:
        - Disponível apenas para ADMIN
        - Eventos podem ser cancelados conforme vontade da administração ou problemas que impeçam seu acontecimento
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<EventoResponseDTO> cancelar(@PathVariable UUID id) {
        Evento evento = eventoService.cancelarEvento(id);
        return ResponseEntity.ok(new EventoResponseDTO(evento));
    }
}
