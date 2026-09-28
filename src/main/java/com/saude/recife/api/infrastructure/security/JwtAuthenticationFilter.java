package com.saude.recife.api.infrastructure.security;

import com.saude.recife.api.application.port.output.TokenPayload;
import com.saude.recife.api.application.port.output.TokenPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenPort tokenPort;

    public JwtAuthenticationFilter(TokenPort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            Optional<TokenPayload> payload = tokenPort.validar(token);

            payload.ifPresent(dados -> {
                var authority = new SimpleGrantedAuthority("ROLE_" + dados.tipoUsuario().name());
                var authentication = new UsernamePasswordAuthenticationToken(
                        dados.email(), null, List.of(authority));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }

        filterChain.doFilter(request, response);
    }
}
