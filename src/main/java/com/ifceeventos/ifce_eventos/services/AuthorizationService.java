package com.ifceeventos.ifce_eventos.services;

import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import com.ifceeventos.ifce_eventos.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;

    // permite consultar os usuários no banco de dados pelo email
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // procura pelo EMAIL
        Usuario usuario = repository.findByEmail(username);

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        return usuario;
    }
}
