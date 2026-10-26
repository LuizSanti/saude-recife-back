package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.Agendamento;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoPort {

    Agendamento salvar(
            Agendamento agendamento
    );

    Optional<Agendamento> buscarPorIdEPaciente(
            Long idAgendamento,
            Long idPaciente
    );

    List<Agendamento> listarPorPaciente(
            Long idPaciente
    );

    boolean existeConflitoProfissional(
            Long idProfissional,
            OffsetDateTime inicio,
            OffsetDateTime fim
    );
}