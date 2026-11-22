package com.saude.recife.api.adapter.input.web.dto.response;

import java.time.LocalDate;

public record PerfilPacienteResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        LocalDate dataNascimento,
        String sexo
) {
}
