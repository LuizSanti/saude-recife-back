package com.saude.recife.api.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PacienteRequest(

        @NotBlank(message = "CPF é obrigatório")
        @Size(
                max = 14,
                message = "CPF deve possuir no máximo 14 caracteres"
        )
        String cpf,

        @NotNull(message = "Data de nascimento é obrigatória")
        LocalDate dataNascimento,

        @Size(
                max = 30,
                message = "Sexo deve possuir no máximo 30 caracteres"
        )
        String sexo,

        String observacoes,

        @NotNull(message = "Usuário é obrigatório")
        Long idUsuario
) {
}