package com.saude.recife.api.adapter.input.web.dto.request;

import com.saude.recife.api.domain.model.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarUsuarioRequest(
        @NotBlank String nome,
        String telefone,
        @NotNull TipoUsuario tipoUsuario
) {
}
