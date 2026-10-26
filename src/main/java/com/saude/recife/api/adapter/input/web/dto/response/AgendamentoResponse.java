package com.saude.recife.api.adapter.input.web.dto.response;

import java.time.OffsetDateTime;

public record AgendamentoResponse(

        Long id,

        String codigoConfirmacao,

        String status,

        Long profissionalId,

        Long clinicaId,

        Long especialidadeId,

        OffsetDateTime inicio,

        OffsetDateTime fim,

        OffsetDateTime criadoEm,

        OffsetDateTime canceladoEm

) {
}