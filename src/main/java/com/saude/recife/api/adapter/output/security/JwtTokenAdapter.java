/*package com.saude.recife.api.adapter.output.security;

import com.saude.recife.api.domain.model.Usuario;
import com.saude.recife.api.application.port.output.TokenPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenAdapter implements TokenPort {

    private final SecretKey chave;
    private final long expiracaoMs;

    public JwtTokenAdapter(@Value("${jwt.secret}") String segredo,
                           @Value("${jwt.expiracao-ms}") long expiracaoMs) {
        this.chave = Keys.hmacShaKeyFor(segredo.getBytes());
        this.expiracaoMs = expiracaoMs;
    }

    @Override
    public String gerar(Usuario usuario) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expiracaoMs);

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("tipoUsuario", usuario.getTipoUsuario().name())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(chave)
                .compact();
    }

    @Override
    public boolean validar(String token) {
        return false;
    }

    @Override
    public String obterEmail(String token) {
        return "";
    }

    @Override
    public String obterTipoUsuario(String token) {
        return "";
    }
}*/
package com.saude.recife.api.adapter.output.security;

import com.saude.recife.api.application.port.output.TokenPort;
import com.saude.recife.api.domain.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenAdapter implements TokenPort {

    private final SecretKey chave;
    private final long expiracaoMs;

    public JwtTokenAdapter(
            @Value("${jwt.secret}") String segredo,
            @Value("${jwt.expiracao-ms}") long expiracaoMs) {

        this.chave = Keys.hmacShaKeyFor(
                segredo.getBytes(StandardCharsets.UTF_8)
        );

        this.expiracaoMs = expiracaoMs;
    }

    @Override
    public String gerar(Usuario usuario) {

        Date agora = new Date();
        Date expiracao =
                new Date(agora.getTime() + expiracaoMs);

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim(
                        "tipoUsuario",
                        usuario.getTipoUsuario().name()
                )
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(chave)
                .compact();
    }

    @Override
    public boolean validar(String token) {

        try {

            Jwts.parser()
                    .verifyWith(chave)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }

    @Override
    public String obterEmail(String token) {

        return obterClaims(token)
                .getSubject();
    }

    @Override
    public String obterTipoUsuario(String token) {

        return obterClaims(token)
                .get(
                        "tipoUsuario",
                        String.class
                );
    }

    private Claims obterClaims(String token) {

        return Jwts.parser()
                .verifyWith(chave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
