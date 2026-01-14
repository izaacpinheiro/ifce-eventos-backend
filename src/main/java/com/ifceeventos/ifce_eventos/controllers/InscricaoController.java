package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.inscricao.InscricaoRequestDTO;
import com.ifceeventos.ifce_eventos.infra.security.SecurityConfig;
import com.ifceeventos.ifce_eventos.services.InscricaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/inscricao")
@Tag(name = "Inscrições", description = "Endpoint responsável pelo gerenciamento das inscrições de usuários em agendamentos")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping
    @Operation(summary = "Se inscrever em um agendamento", description = """
        Possibilita um usuário do sistema se inscrever em um agendamento pelo seu ID.
        
        Regras:
        - Qualquer usuário cadastrado no sistema pode se inscrever em agendamentos
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inscrição realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dado inválido"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<?> inscrever(@RequestBody @Valid InscricaoRequestDTO dto, Authentication authentication) {
        String emailUsuario = authentication.getName();
        inscricaoService.inscrever(dto.agendamentoId(), emailUsuario);
        return ResponseEntity.ok().build();
    }
}
