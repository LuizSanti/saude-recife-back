package com.saude.recife.api.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotNull;

public record AgendamentoRequest(

        @NotNull
        Long disponibilidadeId

) {
}