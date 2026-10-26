package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.DisponibilidadeUseCase;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.application.service.DisponibilidadeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DisponibilidadeConfig {

    @Bean
    public DisponibilidadeUseCase disponibilidadeUseCase(
            DisponibilidadePort disponibilidadePort) {

        return new DisponibilidadeService(
                disponibilidadePort
        );
    }
}