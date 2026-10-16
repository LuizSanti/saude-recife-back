package com.saude.recife.api.adapter.output.persistence.adapter;

import com.saude.recife.api.adapter.output.persistence.entity.ClinicaEntity;
import com.saude.recife.api.adapter.output.persistence.mapper.ClinicaPersistenceMapper;
import com.saude.recife.api.adapter.output.persistence.repository.ClinicaRepository;
import com.saude.recife.api.application.port.output.ClinicaPort;
import com.saude.recife.api.domain.model.Clinica;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClinicaPersistenceAdapter implements ClinicaPort {

    private final ClinicaRepository repository;
    private final ClinicaPersistenceMapper mapper;

    public ClinicaPersistenceAdapter(
            ClinicaRepository repository,
            ClinicaPersistenceMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Clinica salvar(Clinica clinica) {

        ClinicaEntity entity = mapper.paraEntity(clinica);

        ClinicaEntity salvo = repository.save(entity);

        return mapper.paraDomain(salvo);
    }

    @Override
    public Optional<Clinica> buscarPorId(Long id) {

        return repository.findById(id)
                .map(mapper::paraDomain);
    }

    @Override
    public List<Clinica> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::paraDomain)
                .toList();
    }

    @Override
    public boolean existePorCnpj(String cnpj) {
        return repository.existsByCnpj(cnpj);
    }
}