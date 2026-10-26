package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.AgendamentoUseCase;
import com.saude.recife.api.application.port.output.AgendamentoPort;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.application.port.output.PacientePort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.application.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;

@Configuration
public class AgendamentoConfig {

    @Bean
    public AgendamentoUseCase agendamentoUseCase(

            AgendamentoPort agendamentoPort,

            DisponibilidadePort disponibilidadePort,

            UsuarioPort usuarioPort,

            PacientePort pacientePort,

            @Value(
                    "${app.time-zone:America/Recife}"
            )
            String timeZone) {

        return new AgendamentoService(
                agendamentoPort,
                disponibilidadePort,
                usuarioPort,
                pacientePort,
                ZoneId.of(timeZone)
        );
    }
}