package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.EspecialidadeUseCase;
import com.saude.recife.api.application.port.output.EspecialidadePort;
import com.saude.recife.api.application.service.EspecialidadeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EspecialidadeConfig {

    @Bean
    public EspecialidadeUseCase especialidadeUseCase(
            EspecialidadePort especialidadePort) {

        return new EspecialidadeService(especialidadePort);
    }
}