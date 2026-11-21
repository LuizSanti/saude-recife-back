package com.saude.recife.api.adapter.input.web.dto.response;

import java.time.OffsetDateTime;

public record AgendamentoExameResponse(
        Long id,
        Long disponibilidadeId,
        Long exameId,
        Long clinicaId,
        String status,
        String codigoConfirmacao,
        OffsetDateTime inicio,
        OffsetDateTime fim
) {
}
