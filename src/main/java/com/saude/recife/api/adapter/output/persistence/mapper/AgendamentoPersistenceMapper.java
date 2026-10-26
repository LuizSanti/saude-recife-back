package com.saude.recife.api.adapter.output.persistence.mapper;

import com.saude.recife.api.adapter.output.persistence.entity.AgendamentoEntity;
import com.saude.recife.api.domain.model.Agendamento;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoPersistenceMapper {

    public AgendamentoEntity paraEntity(
            Agendamento agendamento) {

        return new AgendamentoEntity(
                agendamento.getId(),
                agendamento.getIdPaciente(),
                agendamento.getIdProfissional(),
                agendamento.getIdClinica(),
                agendamento.getIdEspecialidade(),
                agendamento.getIdGrade(),
                agendamento.getInicio(),
                agendamento.getFim(),
                agendamento.getStatus(),
                agendamento.getCodigoConfirmacao(),
                agendamento.getCriadoEm(),
                agendamento.getCanceladoEm(),
                agendamento.getMotivoCancelamento()
        );
    }

    public Agendamento paraDomain(
            AgendamentoEntity entity) {

        return new Agendamento(
                entity.getId(),
                entity.getIdPaciente(),
                entity.getIdProfissional(),
                entity.getIdClinica(),
                entity.getIdEspecialidade(),
                entity.getIdGrade(),
                entity.getInicio(),
                entity.getFim(),
                entity.getStatus(),
                entity.getCodigoConfirmacao(),
                entity.getCriadoEm(),
                entity.getCanceladoEm(),
                entity.getMotivoCancelamento()
        );
    }
}