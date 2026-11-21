package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.DisponibilidadeUseCase;
import com.saude.recife.api.application.port.output.AgendamentoPort;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.domain.model.Disponibilidade;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class DisponibilidadeService
        implements DisponibilidadeUseCase {

    private final DisponibilidadePort disponibilidadePort;
    private final AgendamentoPort agendamentoPort;
    private final ZoneId zoneId;

    public DisponibilidadeService(
            DisponibilidadePort disponibilidadePort,
            AgendamentoPort agendamentoPort,
            ZoneId zoneId) {

        this.disponibilidadePort = disponibilidadePort;
        this.agendamentoPort = agendamentoPort;
        this.zoneId = zoneId;
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
                )
                .stream()
                .filter(disponibilidade -> !agendamentoPort.existeConflitoProfissional(
                        disponibilidade.getIdProfissional(),
                        disponibilidade.getData()
                                .atTime(disponibilidade.getHoraInicio())
                                .atZone(zoneId)
                                .toOffsetDateTime(),
                        disponibilidade.getData()
                                .atTime(disponibilidade.getHoraFim())
                                .atZone(zoneId)
                                .toOffsetDateTime()
                ))
                .toList();
    }
}