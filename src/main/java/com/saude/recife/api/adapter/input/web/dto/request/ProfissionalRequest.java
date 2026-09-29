package com.saude.recife.api.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProfissionalRequest(

        @NotBlank(message = "CPF é obrigatório")
        @Size(
                max = 14,
                message = "CPF deve possuir no máximo 14 caracteres"
        )
        String cpf,

        @NotBlank(message = "Conselho é obrigatório")
        @Size(
                max = 20,
                message = "Conselho deve possuir no máximo 20 caracteres"
        )
        String conselho,

        @NotBlank(
                message = "Registro profissional é obrigatório"
        )
        @Size(
                max = 20,
                message =
                        "Registro profissional deve possuir no máximo 20 caracteres"
        )
        String registroProfissional,

        @NotBlank(message = "UF do registro é obrigatória")
        @Size(
                min = 2,
                max = 2,
                message = "UF deve possuir exatamente 2 caracteres"
        )
        String ufRegistro,

        @NotNull(message = "Usuário é obrigatório")
        Long idUsuario
) {
}