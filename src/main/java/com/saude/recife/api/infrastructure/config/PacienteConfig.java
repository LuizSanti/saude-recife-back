package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.PacienteUseCase;
import com.saude.recife.api.application.port.output.PacientePort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.application.service.PacienteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PacienteConfig {

    @Bean
    public PacienteUseCase pacienteUseCase(
            PacientePort pacientePort,
            UsuarioPort usuarioPort) {

        return new PacienteService(
                pacientePort,
                usuarioPort
        );
    }
}