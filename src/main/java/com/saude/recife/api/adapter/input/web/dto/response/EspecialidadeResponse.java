package com.saude.recife.api.adapter.input.web.dto.response;

public record EspecialidadeResponse(
        Long id,
        String nome,
        String descricao,
        boolean ativo
        )
    {
}