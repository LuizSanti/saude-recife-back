package com.saude.recife.api.adapter.input.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClinicaRequest(

        @NotBlank(message = "Nome é obrigatório")
        @Size(
                max = 150,
                message = "Nome deve ter no máximo 150 caracteres"
        )
        String nome,

        @NotBlank(message = "CNPJ é obrigatório")
        @Size(
                max = 18,
                message = "CNPJ deve ter no máximo 18 caracteres"
        )
        String cnpj,

        @Size(
                max = 20,
                message = "Telefone deve ter no máximo 20 caracteres"
        )
        String telefone,

        @Email(message = "E-mail inválido")
        @Size(
                max = 150,
                message = "E-mail deve ter no máximo 150 caracteres"
        )
        String email,

        @Size(
                max = 180,
                message = "Logradouro deve ter no máximo 180 caracteres"
        )
        String logradouro,

        @Size(
                max = 20,
                message = "Número deve ter no máximo 20 caracteres"
        )
        String numero,

        @Size(
                max = 100,
                message = "Bairro deve ter no máximo 100 caracteres"
        )
        String bairro,

        @Size(
                max = 100,
                message = "Cidade deve ter no máximo 100 caracteres"
        )
        String cidade,

        @Size(
                min = 2,
                max = 2,
                message = "UF deve possuir 2 caracteres"
        )
        String uf
) {
}