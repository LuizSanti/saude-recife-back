package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.AgendamentoExame;
import com.saude.recife.api.domain.model.DisponibilidadeExame;
import com.saude.recife.api.domain.model.Exame;

import java.time.LocalDate;
import java.util.List;

public interface ExameAgendamentoUseCase {

    List<Exame> listarExames();

    List<DisponibilidadeExame> consultarDisponibilidades(
            LocalDate data,
            Long clinicaId,
            Long exameId
    );

    AgendamentoExame agendar(
            Long disponibilidadeId,
            String emailUsuario
    );
}
