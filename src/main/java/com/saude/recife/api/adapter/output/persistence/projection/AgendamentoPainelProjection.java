package com.saude.recife.api.adapter.output.persistence.projection;

import com.saude.recife.api.domain.model.StatusAgendamento;

import java.time.OffsetDateTime;

public record AgendamentoPainelProjection(
        Long id,
        String pacienteNome,
        String profissionalNome,
        String clinicaNome,
        String especialidadeNome,
        OffsetDateTime inicio,
        OffsetDateTime fim,
        StatusAgendamento status
) {
}
