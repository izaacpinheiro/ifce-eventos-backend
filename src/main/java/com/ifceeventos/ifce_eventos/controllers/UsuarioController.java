package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.agendamento.AgendamentoUsuarioResponseDTO;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.domain.usuario.UsuarioInfosDTO;
import com.ifceeventos.ifce_eventos.infra.security.SecurityConfig;
import com.ifceeventos.ifce_eventos.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuarios", description = "Endpoint responsável pelo gerenciamento das informaçẽos dos usuários do sistema")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/me/agendamentos")
    @Operation(summary = "Listar agendamentos inscritos", description = """
        Retorna todos os agendamentos que o usuário está inscrito.
        
        Regras:
        - Disponível para todos os usuários do sistema
        - São listados agendamentos que já aconteceram e que ainda estão para acontecer
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamentos retornados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<AgendamentoUsuarioResponseDTO> listarMeusAgendamentos(Authentication authentication) {
        // puxando o usuário a partir do JWT
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // retonna uma lista dos agendamentos do usuário
        return usuarioService.listarAgendamentos(usuario).stream()
                .map(AgendamentoUsuarioResponseDTO::new)
                .toList();
    }

    @GetMapping
    @Operation(summary = "Retona usuário logado", description = """
        Retorna informaçẽos do usuário logado.
        
        Regras:
        - Disponível para todos os usuários do sistema
        - São listados: id, nome, email e tipo de usuário
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity<UsuarioInfosDTO> getInformacoesUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // retorna o email do usuario
        String email =  authentication.getName();

        Usuario usuario = usuarioService.getUsuarioInfos(email);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // retorna informações do usuário
        return ResponseEntity.ok(new UsuarioInfosDTO(usuario));
    }
}
