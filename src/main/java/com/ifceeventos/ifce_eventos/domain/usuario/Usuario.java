package com.ifceeventos.ifce_eventos.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Table(name = "usuario")
@Entity(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;
    private String email;
    private String senha; // será armazenada criptografada
    private TipoUsuario tipoUsuario;

    // Talves precise de uma relacionamento com Inscricao

    public Usuario(String email, String senha, TipoUsuario tipoUsuario){
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.tipoUsuario == TipoUsuario.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_PROFESSOR"), new SimpleGrantedAuthority("ROLE_PARTICIPANTE"));
        } else if (this.tipoUsuario == TipoUsuario.PROFESSOR) {
            return List.of(new SimpleGrantedAuthority("ROLE_PROFESSOR"), new SimpleGrantedAuthority("ROLE_PARTICIPANTE"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_PARTICIPANTE"));
        }
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
