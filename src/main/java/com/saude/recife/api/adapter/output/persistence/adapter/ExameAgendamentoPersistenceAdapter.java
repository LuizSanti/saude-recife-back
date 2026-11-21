package com.saude.recife.api.adapter.output.persistence.adapter;

import com.saude.recife.api.adapter.output.persistence.mapper.ExameAgendamentoPersistenceMapper;
import com.saude.recife.api.adapter.output.persistence.repository.AgendamentoExameRepository;
import com.saude.recife.api.adapter.output.persistence.repository.DisponibilidadeExameRepository;
import com.saude.recife.api.adapter.output.persistence.repository.ExameRepository;
import com.saude.recife.api.application.port.output.ExameAgendamentoPort;
import com.saude.recife.api.domain.model.AgendamentoExame;
import com.saude.recife.api.domain.model.DisponibilidadeExame;
import com.saude.recife.api.domain.model.Exame;
import com.saude.recife.api.domain.model.StatusAgendamento;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ExameAgendamentoPersistenceAdapter
        implements ExameAgendamentoPort {

    private final ExameRepository exameRepository;
    private final DisponibilidadeExameRepository disponibilidadeRepository;
    private final AgendamentoExameRepository agendamentoRepository;
    private final ExameAgendamentoPersistenceMapper mapper;

    public ExameAgendamentoPersistenceAdapter(
            ExameRepository exameRepository,
            DisponibilidadeExameRepository disponibilidadeRepository,
            AgendamentoExameRepository agendamentoRepository,
            ExameAgendamentoPersistenceMapper mapper) {
        this.exameRepository = exameRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Exame> listarExamesAtivos() {
        return exameRepository.findByAtivoTrueOrderByNomeAsc()
                .stream()
                .map(mapper::paraDomain)
                .toList();
    }

    @Override
    public List<DisponibilidadeExame> consultarDisponibilidades(
            LocalDate data,
            Long clinicaId,
            Long exameId) {
        return disponibilidadeRepository.consultar(data, clinicaId, exameId)
                .stream()
                .map(mapper::paraDomain)
                .toList();
    }

    @Override
    public Optional<DisponibilidadeExame> buscarDisponibilidade(Long id) {
        return disponibilidadeRepository.findById(id)
                .map(mapper::paraDomain);
    }

    @Override
    public boolean existeConflito(
            Long exameId,
            Long clinicaId,
            OffsetDateTime inicio,
            OffsetDateTime fim) {
        return agendamentoRepository.contarConflitos(
                exameId,
                clinicaId,
                inicio,
                fim,
                StatusAgendamento.CONFIRMADO
        ) > 0;
    }

    @Override
    public AgendamentoExame salvar(AgendamentoExame agendamento) {
        return mapper.paraDomain(
                agendamentoRepository.save(mapper.paraEntity(agendamento))
        );
    }
}
