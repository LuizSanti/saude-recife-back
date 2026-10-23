package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.DisponibilidadeUseCase;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.domain.model.Disponibilidade;

import java.time.LocalDate;
import java.util.List;

public class DisponibilidadeService
        implements DisponibilidadeUseCase {

    private final DisponibilidadePort disponibilidadePort;

    public DisponibilidadeService(
            DisponibilidadePort disponibilidadePort) {

        this.disponibilidadePort = disponibilidadePort;
    }

    @Override
    public List<Disponibilidade> consultar(
            LocalDate data,
            Long clinicaId,
            Long especialidadeId,
            Long profissionalId) {

        return disponibilidadePort.consultar(
                data,
                clinicaId,
                especialidadeId,
                profissionalId
        );
    }
}