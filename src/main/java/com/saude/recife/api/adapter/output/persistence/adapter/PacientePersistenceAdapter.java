package com.saude.recife.api.adapter.output.persistence.adapter;

import com.saude.recife.api.adapter.output.persistence.entity.PacienteEntity;
import com.saude.recife.api.adapter.output.persistence.mapper.PacientePersistenceMapper;
import com.saude.recife.api.adapter.output.persistence.repository.PacienteRepository;
import com.saude.recife.api.application.port.output.PacientePort;
import com.saude.recife.api.domain.model.Paciente;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PacientePersistenceAdapter
        implements PacientePort {

    private final PacienteRepository repository;
    private final PacientePersistenceMapper mapper;

    public PacientePersistenceAdapter(
            PacienteRepository repository,
            PacientePersistenceMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Paciente salvar(Paciente paciente) {

        PacienteEntity entity =
                mapper.paraEntity(paciente);

        PacienteEntity salvo =
                repository.save(entity);

        return mapper.paraDomain(salvo);
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {

        return repository.findById(id)
                .map(mapper::paraDomain);
    }

    @Override
    public List<Paciente> listar() {

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
}