package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.lugar.Lugar;
import com.ifceeventos.ifce_eventos.domain.lugar.LugarResponseDTO;
import com.ifceeventos.ifce_eventos.infra.security.SecurityConfig;
import com.ifceeventos.ifce_eventos.repositories.LugarRepository;
import com.ifceeventos.ifce_eventos.services.LugarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/lugar")
@Tag(name = "Lugares", description = "Endpoint responsável pelo gerenciamento dos lugares do sistema")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class LugarController {

    @Autowired
    private LugarRepository lugarRepository;

    @Autowired
    private LugarService lugarService;

    @GetMapping("/listar")
    @Operation(summary = "Listar lugares", description = """
        Retorna todos os lugares cadastrados no IFCE Campus Crato.
        
        Regras:
        - Disponível apenas para ADMIN
        - Para se fazer agendamentos é necessário passar o ID de um lugar
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lugares retornados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<Lugar> listar() {
        return lugarRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pesquisa lugar por ID", description = """
        Retorna todas as informaçõs de um lugar.
        - Disponível para todos os usuários do sistema
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lugar retornado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<LugarResponseDTO> buscarLugarPorId(@PathVariable UUID id) {
        Lugar lugar = lugarService.buscarLugarPorId(id);
        return ResponseEntity.ok(new LugarResponseDTO(lugar));
    }

}
