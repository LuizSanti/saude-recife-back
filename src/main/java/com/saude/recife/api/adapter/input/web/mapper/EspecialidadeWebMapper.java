package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.EspecialidadeResponse;
import com.saude.recife.api.domain.model.Especialidade;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadeWebMapper {

    public EspecialidadeResponse paraResponse(
            Especialidade especialidade) {

        return new EspecialidadeResponse(
                especialidade.getId(),
                especialidade.getNome(),
                especialidade.getDescricao(),
                especialidade.isAtivo()
        );
    }
}