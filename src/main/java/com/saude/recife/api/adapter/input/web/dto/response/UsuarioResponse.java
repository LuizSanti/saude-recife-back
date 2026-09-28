package com.saude.recife.api.adapter.input.web.dto.response;

import com.saude.recife.api.domain.model.TipoUsuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        TipoUsuario tipoUsuario,
        boolean ativo,
        LocalDateTime criadoEm
) {
}
