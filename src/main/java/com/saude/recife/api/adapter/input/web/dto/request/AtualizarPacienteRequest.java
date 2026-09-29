package com.saude.recife.api.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AtualizarPacienteRequest(

        @NotBlank(message = "CPF é obrigatório")
        @Size(max = 14)
        String cpf,

        @NotNull(message = "Data de nascimento é obrigatória")
        LocalDate dataNascimento,

        @Size(max = 30)
        String sexo,

        String observacoes
) {
}