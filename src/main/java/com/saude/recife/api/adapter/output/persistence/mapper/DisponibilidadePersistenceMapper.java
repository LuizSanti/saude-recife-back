package com.saude.recife.api.adapter.output.persistence.mapper;

import com.saude.recife.api.adapter.output.persistence.entity.DisponibilidadeEntity;
import com.saude.recife.api.domain.model.Disponibilidade;
import org.springframework.stereotype.Component;

@Component
public class DisponibilidadePersistenceMapper {

    public Disponibilidade paraDomain(
            DisponibilidadeEntity entity) {

        return new Disponibilidade(
                entity.getId(),
                entity.getIdProfissional(),
                entity.getIdClinica(),
                entity.getIdEspecialidade(),
                entity.getData(),
                entity.getHoraInicio(),
                entity.getHoraFim(),
                entity.isAtivo()
        );
    }
}