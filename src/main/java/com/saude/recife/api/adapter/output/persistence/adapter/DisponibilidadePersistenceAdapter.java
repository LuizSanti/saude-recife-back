package com.saude.recife.api.adapter.output.persistence.adapter;

import com.saude.recife.api.adapter.output.persistence.mapper.DisponibilidadePersistenceMapper;
import com.saude.recife.api.adapter.output.persistence.repository.DisponibilidadeRepository;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.domain.model.Disponibilidade;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class DisponibilidadePersistenceAdapter
        implements DisponibilidadePort {

    private final DisponibilidadeRepository repository;
    private final DisponibilidadePersistenceMapper mapper;

    public DisponibilidadePersistenceAdapter(
            DisponibilidadeRepository repository,
            DisponibilidadePersistenceMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Disponibilidade> consultar(
            LocalDate data,
            Long clinicaId,
            Long especialidadeId,
            Long profissionalId) {

        return repository.consultar(
                        data,
                        clinicaId,
                        especialidadeId,
                        profissionalId
                )
                .stream()
                .map(mapper::paraDomain)
                .toList();
    }

    @Override
    public boolean existeSobreposicao(
            Long profissionalId,
            Long clinicaId,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim) {

        return repository.contarSobreposicoes(
                profissionalId,
                clinicaId,
                data,
                horaInicio,
                horaFim
        ) > 0;
    }

    @Override
    public Optional<Disponibilidade> buscarPorId(Long id) {

        return repository
                .findById(id)
                .map(mapper::paraDomain);
    }
}