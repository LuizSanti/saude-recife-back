package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.Agendamento;
import com.saude.recife.api.domain.model.StatusAgendamento;

import java.util.List;

public interface AgendamentoUseCase {

    Agendamento agendar(
            Long idDisponibilidade,
            String emailUsuario
    );

    Agendamento cancelar(
            Long idAgendamento,
            String emailUsuario
    );

    List<Agendamento> listarMeus(
            String emailUsuario
    );

    List<Agendamento> listarAgendaProfissional(String emailUsuario);

    Agendamento alterarStatusPorProfissional(
            Long idAgendamento,
            StatusAgendamento status,
            String motivoCancelamento,
            String emailUsuario
    );
}