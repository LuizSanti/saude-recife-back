package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.service.AutenticacaoService;
import com.saude.recife.api.application.port.input.AutenticarUsuarioUseCase;
import com.saude.recife.api.application.port.output.CriptografiaPort;
import com.saude.recife.api.application.port.output.TokenPort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutenticacaoConfig {

    @Bean
    public AutenticarUsuarioUseCase autenticarUsuarioUseCase(UsuarioPort usuarioPort,
                                                             CriptografiaPort criptografiaPort,
                                                             TokenPort tokenPort) {
        return new AutenticacaoService(usuarioPort, criptografiaPort, tokenPort);
    }
}
