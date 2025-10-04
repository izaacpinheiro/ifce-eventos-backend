package com.ifceeventos.ifce_eventos.repositories;

import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
