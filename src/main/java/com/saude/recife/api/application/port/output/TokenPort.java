package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.Usuario;

public interface TokenPort {

    String gerar(Usuario usuario);

    boolean validar(String token);

    String obterEmail(String token);

    String obterTipoUsuario(String token);
}
