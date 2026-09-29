package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.ProfissionalResponse;
import com.saude.recife.api.domain.model.Profissional;
import org.springframework.stereotype.Component;

@Component
public class ProfissionalWebMapper {

    public ProfissionalResponse paraResponse(
            Profissional profissional) {

        return new ProfissionalResponse(
                profissional.getId(),
                profissional.getCpf(),
                profissional.getConselho(),
                profissional.getRegistroProfissional(),
                profissional.getUfRegistro(),
                profissional.isAtivo(),
                profissional.getIdUsuario()
        );
    }
}