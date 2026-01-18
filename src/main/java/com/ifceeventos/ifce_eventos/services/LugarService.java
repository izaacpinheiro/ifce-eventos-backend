package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.lugar.Lugar;
import com.ifceeventos.ifce_eventos.repositories.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LugarService {

    @Autowired
    private LugarRepository lugarRepository;

    public Lugar buscarLugarPorId(UUID id) {
        return  lugarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));
    }
}
