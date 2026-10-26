package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.DisponibilidadeResponse;
import com.saude.recife.api.domain.model.Disponibilidade;
import org.springframework.stereotype.Component;

@Component
public class DisponibilidadeWebMapper {

    public DisponibilidadeResponse paraResponse(
            Disponibilidade disponibilidade) {

        return new DisponibilidadeResponse(
                disponibilidade.getId(),
                disponibilidade.getIdProfissional(),
                disponibilidade.getIdClinica(),
                disponibilidade.getIdEspecialidade(),
                disponibilidade.getData(),
                disponibilidade.getHoraInicio(),
                disponibilidade.getHoraFim()
        );
    }
}