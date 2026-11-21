package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.ExameAgendamentoUseCase;
import com.saude.recife.api.application.port.output.ExameAgendamentoPort;
import com.saude.recife.api.application.port.output.PacientePort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.application.service.ExameAgendamentoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;

@Configuration
public class ExameAgendamentoConfig {

    @Bean
    public ExameAgendamentoUseCase exameAgendamentoUseCase(
            ExameAgendamentoPort exameAgendamentoPort,
            UsuarioPort usuarioPort,
            PacientePort pacientePort,
            @Value("${app.time-zone:America/Recife}") String timeZone) {
        return new ExameAgendamentoService(
                exameAgendamentoPort,
                usuarioPort,
                pacientePort,
                ZoneId.of(timeZone)
        );
    }
}
