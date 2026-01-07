package com.ifceeventos.ifce_eventos.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// config padrão do spring security
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // usado para filtro nas requisições que quero que sejam autenticadas
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/evento").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/api/avento/pendentes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/evento/{id}/aprovar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/evento/{id}/recusar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/avento/aprovados").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/register/professor").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/agendamento").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/agendamento/listar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/lugar/listar").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // retorna classe para fazer a criptografia de informações
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
