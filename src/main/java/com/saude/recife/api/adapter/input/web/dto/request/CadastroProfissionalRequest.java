package com.saude.recife.api.adapter.input.web.dto.request;

import jakarta.validation.constraints.*;

public record CadastroProfissionalRequest(
        @NotBlank @Size(max = 150) String nome,
        @NotBlank @Email @Size(max = 150) String email,
        @NotBlank @Size(min = 6, max = 72) String senha,
        @Size(max = 20) String telefone,
        @NotBlank @Pattern(regexp = "^(\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2})$",
                message = "CPF deve ter 11 dígitos, com ou sem pontuação") String cpf,
        @NotBlank @Size(max = 20) String conselho,
        @NotBlank @Size(max = 20) String registroProfissional,
        @NotBlank @Pattern(regexp = "^[A-Za-z]{2}$", message = "UF deve ter 2 letras") String ufRegistro
) {}
