package com.saude.recife.api.adapter.output.persistence.adapter;

import com.saude.recife.api.adapter.output.persistence.entity.EspecialidadeEntity;
import com.saude.recife.api.adapter.output.persistence.mapper.EspecialidadePersistenceMapper;
import com.saude.recife.api.adapter.output.persistence.repository.EspecialidadeRepository;
import com.saude.recife.api.application.port.output.EspecialidadePort;
import com.saude.recife.api.domain.model.Especialidade;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EspecialidadePersistenceAdapter
        implements EspecialidadePort {

    private final EspecialidadeRepository repository;
    private final EspecialidadePersistenceMapper mapper;

    public EspecialidadePersistenceAdapter(
            EspecialidadeRepository repository,
            EspecialidadePersistenceMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Especialidade salvar(
            Especialidade especialidade) {

        EspecialidadeEntity entity =
                mapper.paraEntity(especialidade);

        EspecialidadeEntity salvo =
                repository.save(entity);

        return mapper.paraDomain(salvo);
    }

    @Override
    public Optional<Especialidade> buscarPorId(Long id) {

        return repository.findById(id)
                .map(mapper::paraDomain);
    }

    @Override
    public List<Especialidade> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::paraDomain)
                .toList();
    }
}