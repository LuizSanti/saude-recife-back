package com.saude.recife.api.adapter.input.web.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record DisponibilidadeExameResponse(
        Long id,
        Long exameId,
        Long clinicaId,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim
) {
}
