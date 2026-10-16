package com.saude.recife.api.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarProfissionalRequest(

        @NotBlank(message = "CPF é obrigatório")
        @Size(max = 14)
        String cpf,

        @NotBlank(message = "Conselho é obrigatório")
        @Size(max = 20)
        String conselho,

        @NotBlank(message = "Registro profissional é obrigatório")
        @Size(max = 20)
        String registroProfissional,

        @NotBlank(message = "UF do registro é obrigatória")
        @Size(min = 2, max = 2)
        String ufRegistro
) {
}