package com.saude.recife.api.adapter.input.web.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record DisponibilidadeResponse(
        Long id,
        Long profissionalId,
        Long clinicaId,
        Long especialidadeId,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim
) {
}