package com.saude.recife.api.adapter.output.persistence.mapper;

import com.saude.recife.api.adapter.output.persistence.entity.AgendamentoExameEntity;
import com.saude.recife.api.adapter.output.persistence.entity.DisponibilidadeExameEntity;
import com.saude.recife.api.adapter.output.persistence.entity.ExameEntity;
import com.saude.recife.api.domain.model.AgendamentoExame;
import com.saude.recife.api.domain.model.DisponibilidadeExame;
import com.saude.recife.api.domain.model.Exame;
import org.springframework.stereotype.Component;

@Component
public class ExameAgendamentoPersistenceMapper {

    public Exame paraDomain(ExameEntity entity) {
        return new Exame(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.isAtivo()
        );
    }

    public DisponibilidadeExame paraDomain(DisponibilidadeExameEntity entity) {
        return new DisponibilidadeExame(
                entity.getId(),
                entity.getExameId(),
                entity.getClinicaId(),
                entity.getData(),
                entity.getHoraInicio(),
                entity.getHoraFim(),
                entity.isAtivo()
        );
    }

    public AgendamentoExameEntity paraEntity(AgendamentoExame agendamento) {
        AgendamentoExameEntity entity = new AgendamentoExameEntity();
        entity.setId(agendamento.id());
        entity.setPacienteId(agendamento.pacienteId());
        entity.setDisponibilidadeId(agendamento.disponibilidadeId());
        entity.setExameId(agendamento.exameId());
        entity.setClinicaId(agendamento.clinicaId());
        entity.setInicio(agendamento.inicio());
        entity.setFim(agendamento.fim());
        entity.setStatus(agendamento.status());
        entity.setCodigoConfirmacao(agendamento.codigoConfirmacao());
        entity.setCriadoEm(agendamento.criadoEm());
        return entity;
    }

    public AgendamentoExame paraDomain(AgendamentoExameEntity entity) {
        return new AgendamentoExame(
                entity.getId(),
                entity.getPacienteId(),
                entity.getDisponibilidadeId(),
                entity.getExameId(),
                entity.getClinicaId(),
                entity.getInicio(),
                entity.getFim(),
                entity.getStatus(),
                entity.getCodigoConfirmacao(),
                entity.getCriadoEm()
        );
    }
}
