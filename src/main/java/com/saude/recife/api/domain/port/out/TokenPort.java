package com.saude.recife.api.domain.port.out;

import com.saude.recife.api.domain.model.Usuario;

public interface TokenPort {

    String gerar(Usuario usuario);
}
