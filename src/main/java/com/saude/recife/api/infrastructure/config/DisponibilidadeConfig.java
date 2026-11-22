package com.saude.recife.api.infrastructure.config;

import com.saude.recife.api.application.port.input.DisponibilidadeUseCase;
import com.saude.recife.api.application.port.output.AgendamentoPort;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.application.port.output.ProfissionalPort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.application.port.output.ClinicaPort;
import com.saude.recife.api.application.port.output.EspecialidadePort;
import com.saude.recife.api.application.service.DisponibilidadeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;

@Configuration
public class DisponibilidadeConfig {

    @Bean
    public DisponibilidadeUseCase disponibilidadeUseCase(
            DisponibilidadePort disponibilidadePort,
            AgendamentoPort agendamentoPort,
            ProfissionalPort profissionalPort,
            UsuarioPort usuarioPort,
            ClinicaPort clinicaPort,
            EspecialidadePort especialidadePort,
            @Value("${app.time-zone:America/Recife}") String timeZone) {

        return new DisponibilidadeService(
                disponibilidadePort,
                agendamentoPort,
                profissionalPort,
                usuarioPort,
                clinicaPort,
                especialidadePort,
                ZoneId.of(timeZone)
        );
    }
}