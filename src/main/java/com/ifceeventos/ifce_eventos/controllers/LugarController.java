package com.ifceeventos.ifce_eventos.controllers;

import com.ifceeventos.ifce_eventos.domain.lugar.Lugar;
import com.ifceeventos.ifce_eventos.repositories.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/lugar")
public class LugarController {

    @Autowired
    private LugarRepository lugarRepository;

    @GetMapping("/listar")
    public List<Lugar> listar() {
        return lugarRepository.findAll();
    }
}
