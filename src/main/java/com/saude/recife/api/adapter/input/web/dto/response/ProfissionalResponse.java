package com.saude.recife.api.adapter.input.web.dto.response;

public record ProfissionalResponse(
        Long id,
        String nome,
        String cpf,
        String conselho,
        String registroProfissional,
        String ufRegistro,
        boolean ativo,
        Long idUsuario
) {
}