package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.usuario.RegisterDTO;
import com.ifceeventos.ifce_eventos.domain.usuario.TipoUsuario;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.infra.security.SecurityConfig;
import com.ifceeventos.ifce_eventos.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@Tag(name = "Administração", description = "Endpoints responsáveis para administração do sistema")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AdminUserController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/register/professor")
    @Operation(summary = "Registra novo professor", description = """
        Cria o registro de um novo professor no sistema.
        
        Regras:
        - Disponível apenas para ADMIN
        - Não podem existir professores com mesmo email
        - Única forma de registro para novos professores
        """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public ResponseEntity registerProfessor(@RequestBody @Valid RegisterDTO data) {
        // caso já existem algum registro com o email
        if (this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String senhaCriptografada = new BCryptPasswordEncoder().encode(data.senha());
        // cria um novo usuário do tipo professor
        Usuario novoUsuario = new Usuario(data.email(), senhaCriptografada, data.nome(), TipoUsuario.PROFESSOR);

        // salvando novo usuário no bd
        this.repository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }

}
