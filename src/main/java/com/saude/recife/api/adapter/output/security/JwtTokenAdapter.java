package com.saude.recife.api.adapter.output.security;

import com.saude.recife.api.application.port.output.TokenPayload;
import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;
import com.saude.recife.api.application.port.output.TokenPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

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
    public Optional<TokenPayload> validar(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(chave)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String email = claims.getSubject();
            TipoUsuario tipoUsuario = TipoUsuario.valueOf(claims.get("tipoUsuario", String.class));

            return Optional.of(new TokenPayload(email, tipoUsuario));
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
