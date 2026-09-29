package com.saude.recife.api.adapter.output.persistence.mapper;

import com.saude.recife.api.adapter.output.persistence.entity.EspecialidadeEntity;
import com.saude.recife.api.domain.model.Especialidade;
import org.springframework.stereotype.Component;

@Component
public class EspecialidadePersistenceMapper {
    public EspecialidadeEntity paraEntity(
            Especialidade especialidade) {

        return new EspecialidadeEntity(
                especialidade.getId(),
                especialidade.getNome(),
                especialidade.getDescricao(),
                especialidade.isAtivo()
        );
    }

    public Especialidade paraDomain(
            EspecialidadeEntity entity) {

        return new Especialidade(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.isAtivo()
        );
    }
}
