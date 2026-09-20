package com.saude.recife.api.adapter.input.web.dto.response;

public record LoginResponse(
        String token,
        String tipoUsuario
) {
}
