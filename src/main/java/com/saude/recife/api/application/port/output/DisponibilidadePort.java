package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.Disponibilidade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DisponibilidadePort {
    List<Disponibilidade> consultar(
            LocalDate data,
            Long clinicaId,
            Long especialidadeId,
            Long profissionalId
    );

    boolean existeSobreposicao(
            Long profissionalId,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim
    );

    boolean possuiAssociacoesValidas(Long idDisponibilidade);
    Optional<Disponibilidade> buscarPorId(Long id);

    Optional<Disponibilidade> buscarPorIdEProfissional(
            Long id,
            Long idProfissional
    );

    List<Disponibilidade> listarPorProfissional(Long idProfissional);

    Disponibilidade salvar(Disponibilidade disponibilidade);

    List<Disponibilidade> salvarTodas(List<Disponibilidade> disponibilidades);
}
