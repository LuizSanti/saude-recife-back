package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.LoginResponse;
import com.saude.recife.api.application.port.input.ResultadoAutenticacao;
import org.springframework.stereotype.Component;

@Component
public class AutenticacaoWebMapper {

    public LoginResponse paraLoginResponse(ResultadoAutenticacao resultado) {
        return new LoginResponse(resultado.getToken(), resultado.getTipoUsuario());
    }
}
