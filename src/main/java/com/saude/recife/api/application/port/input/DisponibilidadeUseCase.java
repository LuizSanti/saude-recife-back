package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.Disponibilidade;

import java.time.LocalDate;
import java.util.List;

public interface DisponibilidadeUseCase {
    List<Disponibilidade> consultar(
            LocalDate data,
            Long clinicaId,
            Long especialidadeId,
            Long profissionalId
    );
}
