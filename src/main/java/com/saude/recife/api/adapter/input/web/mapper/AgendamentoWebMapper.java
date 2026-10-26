package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.AgendamentoResponse;
import com.saude.recife.api.domain.model.Agendamento;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoWebMapper {

    public AgendamentoResponse paraResponse(
            Agendamento agendamento) {

        return new AgendamentoResponse(
                agendamento.getId(),
                agendamento.getCodigoConfirmacao(),
                agendamento.getStatus().name(),
                agendamento.getIdProfissional(),
                agendamento.getIdClinica(),
                agendamento.getIdEspecialidade(),
                agendamento.getInicio(),
                agendamento.getFim(),
                agendamento.getCriadoEm(),
                agendamento.getCanceladoEm()
        );
    }
}