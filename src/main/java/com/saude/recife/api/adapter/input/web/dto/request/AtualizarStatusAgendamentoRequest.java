package com.saude.recife.api.adapter.input.web.dto.request;

import com.saude.recife.api.domain.model.StatusAgendamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AtualizarStatusAgendamentoRequest(
        @NotNull StatusAgendamento status,
        @Size(max = 500) String motivoCancelamento
) {
}
