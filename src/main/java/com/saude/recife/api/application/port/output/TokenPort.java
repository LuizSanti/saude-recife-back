package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.Usuario;

import java.util.Optional;

public interface TokenPort {

    String gerar(Usuario usuario);

    Optional<TokenPayload> validar(String token);
}
