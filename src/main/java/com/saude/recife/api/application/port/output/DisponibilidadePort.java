package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.Disponibilidade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DisponibilidadePort {
    List<Disponibilidade> consultar(
            LocalDate data,
            Long clinicaId,
            Long especialidadeId,
            Long profissionalId
    );

    boolean existeSobreposicao(
            Long profissionalId,
            Long clinicaId,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim
    );
}
