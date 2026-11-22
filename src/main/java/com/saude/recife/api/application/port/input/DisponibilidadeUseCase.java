package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.Disponibilidade;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalTime;

public interface DisponibilidadeUseCase {
    List<Disponibilidade> consultar(
            LocalDate data,
            Long clinicaId,
            Long especialidadeId,
            Long profissionalId
    );

    List<Disponibilidade> listarDoProfissional(String emailUsuario);

    List<Disponibilidade> criarParaProfissional(
            Long clinicaId,
            Long especialidadeId,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim,
            String emailUsuario
    );

    void inativarDoProfissional(Long id, String emailUsuario);
}
