package com.saude.recife.api.domain.model;

import java.time.OffsetDateTime;

public record AgendamentoExame(
        Long id,
        Long pacienteId,
        Long disponibilidadeId,
        Long exameId,
        Long clinicaId,
        OffsetDateTime inicio,
        OffsetDateTime fim,
        StatusAgendamento status,
        String codigoConfirmacao,
        OffsetDateTime criadoEm
) {
}
