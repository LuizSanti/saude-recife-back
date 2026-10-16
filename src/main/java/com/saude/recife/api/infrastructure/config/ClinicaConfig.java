package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.ClinicaUseCase;
import com.saude.recife.api.application.port.output.ClinicaPort;
import com.saude.recife.api.application.service.ClinicaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClinicaConfig {

    @Bean
    public ClinicaUseCase clinicaUseCase(
            ClinicaPort clinicaPort) {

        return new ClinicaService(clinicaPort);
    }
}