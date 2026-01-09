package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.inscricao.InscricaoRequestDTO;
import com.ifceeventos.ifce_eventos.services.InscricaoService;
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
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    // disponível para todos os usuários cadastrados
    @PostMapping
    public ResponseEntity<?> inscrever(@RequestBody @Valid InscricaoRequestDTO dto, Authentication authentication) {
        String emailUsuario = authentication.getName();
        inscricaoService.inscrever(dto.agendamentoId(), emailUsuario);
        return ResponseEntity.ok().build();
    }
}
