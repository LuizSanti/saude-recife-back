package com.saude.recife.api.adapter.output.persistence.mapper;

import com.saude.recife.api.adapter.output.persistence.entity.ProfissionalEntity;
import com.saude.recife.api.domain.model.Profissional;
import org.springframework.stereotype.Component;

@Component
public class ProfissionalPersistenceMapper {

    public ProfissionalEntity paraEntity(
            Profissional profissional) {

        return new ProfissionalEntity(
                profissional.getId(),
                profissional.getCpf(),
                profissional.getConselho(),
                profissional.getRegistroProfissional(),
                profissional.getUfRegistro(),
                profissional.isAtivo(),
                profissional.getIdUsuario()
        );
    }

    public Profissional paraDomain(
            ProfissionalEntity entity) {

        return new Profissional(
                entity.getId(),
                entity.getCpf(),
                entity.getConselho(),
                entity.getRegistroProfissional(),
                entity.getUfRegistro(),
                entity.isAtivo(),
                entity.getIdUsuario()
        );
    }
}