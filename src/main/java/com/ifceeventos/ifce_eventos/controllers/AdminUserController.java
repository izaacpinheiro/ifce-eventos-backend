package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.usuario.RegisterDTO;
import com.ifceeventos.ifce_eventos.domain.usuario.TipoUsuario;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.repositories.UsuarioRepository;
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
public class AdminUserController {

    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/register/professor")
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
