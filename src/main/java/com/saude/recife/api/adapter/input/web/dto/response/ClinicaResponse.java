package com.saude.recife.api.adapter.input.web.dto.response;

public record ClinicaResponse(
        Long id,
        String nome,
        String cnpj,
        String telefone,
        String email,
        String logradouro,
        String numero,
        String bairro,
        String cidade,
        String uf,
        boolean ativo
) {
}