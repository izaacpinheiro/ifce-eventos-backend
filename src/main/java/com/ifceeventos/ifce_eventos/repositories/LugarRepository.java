package com.ifceeventos.ifce_eventos.repositories;

import com.ifceeventos.ifce_eventos.domain.lugar.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LugarRepository extends JpaRepository<Lugar, UUID> {
}
