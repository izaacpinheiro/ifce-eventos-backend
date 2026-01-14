package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.usuario.*;
import com.ifceeventos.ifce_eventos.infra.security.TokenService;
import com.ifceeventos.ifce_eventos.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@Tag(name = "Autenticação", description = "Endpoints responsáveis pelo gerenciamento de login e registro do sistema")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "Login no sistema", description = """
        Fazer login do sistema utilizando email e senha.
        
        Regras:
        - Qualquer pessoa pode tentar logar no sistema
        - Retorna um token JWT que será usado para autenticação do usuário
        """
    )
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

        // autenticação do email e senha
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // passando o token para o usuário
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Se registrar no sistema", description = """
        Fazer registro no sistema utilizando email, senha e nome.
        
        Regras:
        - Não podem existir usuários com mesmo email
        """
    )
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        // caso já existem algum registro com o email
        if (this.repository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String senhaCriptografada = new BCryptPasswordEncoder().encode(data.senha());
        Usuario novoUsuario = new Usuario(data.email(), senhaCriptografada, data.nome(), TipoUsuario.PARTICIPANTE);

        // salvando novo usuário no bd
        this.repository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}
