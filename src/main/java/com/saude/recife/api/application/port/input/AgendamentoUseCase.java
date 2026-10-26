package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.Agendamento;

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
}