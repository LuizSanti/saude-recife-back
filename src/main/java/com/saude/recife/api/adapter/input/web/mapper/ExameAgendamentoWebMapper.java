package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.AgendamentoExameResponse;
import com.saude.recife.api.adapter.input.web.dto.response.DisponibilidadeExameResponse;
import com.saude.recife.api.adapter.input.web.dto.response.ExameResponse;
import com.saude.recife.api.domain.model.AgendamentoExame;
import com.saude.recife.api.domain.model.DisponibilidadeExame;
import com.saude.recife.api.domain.model.Exame;
import org.springframework.stereotype.Component;

@Component
public class ExameAgendamentoWebMapper {

    public ExameResponse paraResponse(Exame exame) {
        return new ExameResponse(
                exame.id(),
                exame.nome(),
                exame.descricao(),
                exame.ativo()
        );
    }

    public DisponibilidadeExameResponse paraResponse(
            DisponibilidadeExame disponibilidade) {
        return new DisponibilidadeExameResponse(
                disponibilidade.id(),
                disponibilidade.exameId(),
                disponibilidade.clinicaId(),
                disponibilidade.data(),
                disponibilidade.horaInicio(),
                disponibilidade.horaFim()
        );
    }

    public AgendamentoExameResponse paraResponse(
            AgendamentoExame agendamento) {
        return new AgendamentoExameResponse(
                agendamento.id(),
                agendamento.disponibilidadeId(),
                agendamento.exameId(),
                agendamento.clinicaId(),
                agendamento.status().name(),
                agendamento.codigoConfirmacao(),
                agendamento.inicio(),
                agendamento.fim()
        );
    }
}
