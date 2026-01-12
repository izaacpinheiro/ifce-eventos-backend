package com.ifceeventos.ifce_eventos.infra.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@SecurityScheme(name = SecurityConfig.SECURITY, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SecurityConfig {

    // config do swagger
    public static final String SECURITY = "bearerAuth";

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
                        .requestMatchers("/v3/api-docs/**", "swagger-ui/**", "swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/evento").hasRole("PROFESSOR")
                        .requestMatchers(HttpMethod.GET, "/api/avento/pendentes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/evento/{id}/aprovar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/evento/{id}/recusar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/avento/aprovados").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/admin/register/professor").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/agendamento").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/agendamento/listar").hasRole("PARTICIPANTE")
                        .requestMatchers(HttpMethod.GET, "/api/lugar/listar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/inscricao").hasRole("PARTICIPANTE")
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
