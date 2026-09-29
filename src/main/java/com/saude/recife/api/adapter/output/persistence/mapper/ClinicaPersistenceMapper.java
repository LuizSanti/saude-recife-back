package com.saude.recife.api.adapter.output.persistence.mapper;

import com.saude.recife.api.adapter.output.persistence.entity.ClinicaEntity;
import com.saude.recife.api.domain.model.Clinica;
import org.springframework.stereotype.Component;

@Component
public class ClinicaPersistenceMapper {

    public ClinicaEntity paraEntity(Clinica clinica) {

        return new ClinicaEntity(
                clinica.getId(),
                clinica.getNome(),
                clinica.getCnpj(),
                clinica.getTelefone(),
                clinica.getEmail(),
                clinica.getLogradouro(),
                clinica.getNumero(),
                clinica.getBairro(),
                clinica.getCidade(),
                clinica.getUf(),
                clinica.isAtivo()
        );
    }

    public Clinica paraDomain(ClinicaEntity entity) {

        return new Clinica(
                entity.getId(),
                entity.getNome(),
                entity.getCnpj(),
                entity.getTelefone(),
                entity.getEmail(),
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getUf(),
                entity.isAtivo()
        );
    }
}