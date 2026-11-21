package com.saude.recife.api.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public record DisponibilidadeExame(
        Long id,
        Long exameId,
        Long clinicaId,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        boolean ativo
) {
}
