package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.AgendamentoPainelResponse;
import com.saude.recife.api.adapter.output.persistence.projection.AgendamentoPainelProjection;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoPainelWebMapper {
    public AgendamentoPainelResponse paraResponse(
            AgendamentoPainelProjection projection) {

        return new AgendamentoPainelResponse(
                projection.id(),
                projection.pacienteNome(),
                projection.profissionalNome(),
                projection.clinicaNome(),
                projection.especialidadeNome(),
                projection.inicio(),
                projection.fim(),
                projection.status().name()
        );
    }
}
