package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.lugar.Lugar;
import com.ifceeventos.ifce_eventos.infra.security.SecurityConfig;
import com.ifceeventos.ifce_eventos.repositories.LugarRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/lugar")
@Tag(name = "Lugares", description = "Endpoint responsável pelo gerenciamento dos lugares do sistema")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class LugarController {

    @Autowired
    private LugarRepository lugarRepository;

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
}
