package com.saude.recife.api.adapter.input.web.dto.response;

import java.time.OffsetDateTime;

public record AgendamentoPainelResponse(
        Long id,
        String pacienteNome,
        String profissionalNome,
        String clinicaNome,
        String especialidadeNome,
        OffsetDateTime inicio,
        OffsetDateTime fim,
        String status
) {
}
