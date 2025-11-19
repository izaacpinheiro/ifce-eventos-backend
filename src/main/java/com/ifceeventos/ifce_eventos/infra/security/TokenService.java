package com.ifceeventos.ifce_eventos.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ifceeventos.ifce_eventos.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario user){
        try {
            // algoritmo escolhido para criação do JWT
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // criação do token
            String token = JWT.create()
                    .withIssuer("ifce-eventos")
                    .withSubject(user.getEmail())
                    .withClaim("tipoUsuario", user.getTipoUsuario().name())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error while authenticating");
        }
    }

    // função para validar o token JWT
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ifce-eventos")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    // função para gerar o tempo que o token vai expirar
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-3"));
    }
}
