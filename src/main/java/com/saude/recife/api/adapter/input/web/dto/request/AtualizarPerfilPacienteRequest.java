package com.saude.recife.api.adapter.input.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarPerfilPacienteRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
        String nome,

        @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
        String telefone
) {
}
