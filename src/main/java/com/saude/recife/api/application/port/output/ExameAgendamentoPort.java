package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.AgendamentoExame;
import com.saude.recife.api.domain.model.DisponibilidadeExame;
import com.saude.recife.api.domain.model.Exame;

import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExameAgendamentoPort {

    List<Exame> listarExamesAtivos();

    List<DisponibilidadeExame> consultarDisponibilidades(
            LocalDate data,
            Long clinicaId,
            Long exameId
    );

    Optional<DisponibilidadeExame> buscarDisponibilidade(Long id);

    boolean existeConflito(
            Long exameId,
            Long clinicaId,
            OffsetDateTime inicio,
            OffsetDateTime fim
    );

    AgendamentoExame salvar(AgendamentoExame agendamento);
}
