package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.LoginResponse;
import com.saude.recife.api.application.port.input.ResultadoAutenticacao;
import org.springframework.stereotype.Component;

@Component
public class AutenticacaoWebMapper {

    public LoginResponse paraLoginResponse(ResultadoAutenticacao r) {
        return new LoginResponse(r.getToken(), r.getTipoUsuario(),
                r.getId(), r.getNome(), r.getEmail());
    }
}
