package com.saude.recife.api.adapter.output.persistence.adapter;

import com.saude.recife.api.adapter.output.persistence.entity.AgendamentoEntity;
import com.saude.recife.api.adapter.output.persistence.mapper.AgendamentoPersistenceMapper;
import com.saude.recife.api.adapter.output.persistence.repository.AgendamentoRepository;
import com.saude.recife.api.application.port.output.AgendamentoPort;
import com.saude.recife.api.domain.model.Agendamento;
import com.saude.recife.api.domain.model.StatusAgendamento;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class AgendamentoPersistenceAdapter
        implements AgendamentoPort {

    private final AgendamentoRepository repository;
    private final AgendamentoPersistenceMapper mapper;

    public AgendamentoPersistenceAdapter(
            AgendamentoRepository repository,
            AgendamentoPersistenceMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Agendamento salvar(
            Agendamento agendamento) {

        AgendamentoEntity entity =
                mapper.paraEntity(agendamento);

        AgendamentoEntity salvo =
                repository.save(entity);

        return mapper.paraDomain(salvo);
    }

    @Override
    public Optional<Agendamento> buscarPorIdEPaciente(
            Long idAgendamento,
            Long idPaciente) {

        return repository
                .findByIdAndIdPaciente(
                        idAgendamento,
                        idPaciente
                )
                .map(mapper::paraDomain);
    }

    @Override
    public List<Agendamento> listarPorPaciente(
            Long idPaciente) {

        return repository
                .findByIdPacienteOrderByInicioDesc(
                        idPaciente
                )
                .stream()
                .map(mapper::paraDomain)
                .toList();
    }

    @Override
    public boolean existeConflitoProfissional(
            Long idProfissional,
            OffsetDateTime inicio,
            OffsetDateTime fim) {

        return repository
                .contarConflitosProfissional(
                        idProfissional,
                        inicio,
                        fim,
                        StatusAgendamento.CONFIRMADO
                ) > 0;
    }
}