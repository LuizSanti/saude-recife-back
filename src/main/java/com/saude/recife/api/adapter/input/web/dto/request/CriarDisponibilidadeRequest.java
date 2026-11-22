package com.saude.recife.api.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record CriarDisponibilidadeRequest(
        @NotNull Long clinicaId,
        @NotNull Long especialidadeId,
        @NotNull LocalDate data,
        @NotNull LocalTime horaInicio,
        @NotNull LocalTime horaFim
) {
}
