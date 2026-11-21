package com.saude.recife.api.adapter.input.web.dto.response;

public record ExameResponse(
        Long id,
        String nome,
        String descricao,
        boolean ativo
) {
}
