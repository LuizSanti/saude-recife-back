package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.ProfissionalUseCase;
import com.saude.recife.api.application.port.output.ProfissionalPort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.application.service.ProfissionalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProfissionalConfig {

    @Bean
    public ProfissionalUseCase profissionalUseCase(
            ProfissionalPort profissionalPort,
            UsuarioPort usuarioPort) {

        return new ProfissionalService(
                profissionalPort,
                usuarioPort
        );
    }
}