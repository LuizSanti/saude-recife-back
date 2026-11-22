package com.saude.recife.api.adapter.input.web.dto.response;

public record ProfissionalPerfilResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        String conselho,
        String registroProfissional,
        String ufRegistro,
        boolean ativo
) {
}
