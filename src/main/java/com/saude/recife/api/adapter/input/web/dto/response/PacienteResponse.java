package com.saude.recife.api.adapter.input.web.dto.response;

import java.time.LocalDate;

public record PacienteResponse(
        Long id,
        String cpf,
        LocalDate dataNascimento,
        String sexo,
        String observacoes,
        Long idUsuario
) {
}