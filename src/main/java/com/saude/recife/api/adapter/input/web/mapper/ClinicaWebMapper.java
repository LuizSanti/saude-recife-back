package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.ClinicaResponse;
import com.saude.recife.api.domain.model.Clinica;
import org.springframework.stereotype.Component;

@Component
public class ClinicaWebMapper {

    public ClinicaResponse paraResponse(Clinica clinica) {

        return new ClinicaResponse(
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
}