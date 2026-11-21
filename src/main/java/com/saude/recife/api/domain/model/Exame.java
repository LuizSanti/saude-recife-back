package com.saude.recife.api.domain.model;

public record Exame(
        Long id,
        String nome,
        String descricao,
        boolean ativo
) {
}
