package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.agendamento.Agendamento;
import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoRequestDTO;
import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoResponseDTO;
import com.ifceeventos.ifce_eventos.infra.security.SecurityConfig;
import com.ifceeventos.ifce_eventos.services.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/agendamento")
@Tag(name = "Agendamentos", description = "Endpoints responsáveis pelo gerenciamento dos agendamentos do sistema")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    @Operation(summary = "Cria novo agendamento", description = """
        Cria um novo agendamento no sistema.
        
        Regras:
        - Disponível apenas para ADMIN
        - Para um evento ser agendando ele precisa ter sido aprovado
        - Deve ser informado o ID do Evento, ID do Lugar, data, Hora de Início e Hora do Fim
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<Agendamento> create(@RequestBody AgendamentoRequestDTO body) {
        Agendamento novoAgendamento = this.agendamentoService.createAgendamento(body);
        return ResponseEntity.ok(novoAgendamento);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar agendamentos", description = """
        Retorna todos os agendamentos criados que ainda vão acontecer.
        
        Regras:
        - Disponível para todos os usuários do sistema
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamentos retornados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<AgendamentoResponseDTO> listarAgendamentos() {
        return agendamentoService.listarAgendamentos().stream()
                .map(AgendamentoResponseDTO::new)
                .toList();
    }
}
