package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.service.AutenticacaoService;
import com.saude.recife.api.domain.port.in.autenticacao.AutenticarUsuarioUseCase;
import com.saude.recife.api.domain.port.out.CriptografiaPort;
import com.saude.recife.api.domain.port.out.TokenPort;
import com.saude.recife.api.domain.port.out.UsuarioPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public AutenticarUsuarioUseCase autenticarUsuarioUseCase(UsuarioPort usuarioPort,
                                                             CriptografiaPort criptografiaPort,
                                                             TokenPort tokenPort) {
        return new AutenticacaoService(usuarioPort, criptografiaPort, tokenPort);
    }
}
