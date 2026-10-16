package com.saude.recife.api.adapter.input.web.dto.request;

import com.saude.recife.api.domain.model.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CadastrarUsuarioRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, message = "senha deve ter no minimo 6 caracteres") String senha,
        String telefone,
        @NotNull TipoUsuario tipoUsuario
) {
}
