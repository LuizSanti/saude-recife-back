package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.TipoUsuario;

public record TokenPayload(String email, TipoUsuario tipoUsuario) {
}
