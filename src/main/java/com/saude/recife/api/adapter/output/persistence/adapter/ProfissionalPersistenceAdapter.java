package com.saude.recife.api.adapter.output.persistence.adapter;

import com.saude.recife.api.adapter.output.persistence.entity.ProfissionalEntity;
import com.saude.recife.api.adapter.output.persistence.mapper.ProfissionalPersistenceMapper;
import com.saude.recife.api.adapter.output.persistence.repository.ProfissionalRepository;
import com.saude.recife.api.application.port.output.ProfissionalPort;
import com.saude.recife.api.domain.model.Profissional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProfissionalPersistenceAdapter
        implements ProfissionalPort {

    private final ProfissionalRepository repository;
    private final ProfissionalPersistenceMapper mapper;

    public ProfissionalPersistenceAdapter(
            ProfissionalRepository repository,
            ProfissionalPersistenceMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Profissional salvar(
            Profissional profissional) {

        ProfissionalEntity entity =
                mapper.paraEntity(profissional);

        ProfissionalEntity salvo =
                repository.save(entity);

        return mapper.paraDomain(salvo);
    }

    @Override
    public Optional<Profissional> buscarPorId(Long id) {

        return repository.findById(id)
                .map(mapper::paraDomain);
    }

    @Override
    public List<Profissional> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::paraDomain)
                .toList();
    }

    @Override
    public boolean existePorCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public boolean existePorUsuario(Long idUsuario) {
        return repository.existsByIdUsuario(idUsuario);
    }

    @Override
    public boolean existePorRegistro(
            String registroProfissional,
            String conselho,
            String ufRegistro) {

        return repository
                .existsByRegistroProfissionalAndConselhoAndUfRegistro(
                        registroProfissional,
                        conselho,
                        ufRegistro
                );
    }
}