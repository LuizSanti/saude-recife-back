package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.application.port.output.CriptografiaPort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.application.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public UsuarioUseCase usuarioUseCase(UsuarioPort usuarioPort, CriptografiaPort criptografiaPort) {
        return new UsuarioService(usuarioPort, criptografiaPort);
    }
}