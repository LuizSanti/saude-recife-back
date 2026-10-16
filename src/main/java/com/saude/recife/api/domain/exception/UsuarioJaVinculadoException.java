package com.saude.recife.api.domain.exception;

public class UsuarioJaVinculadoException extends RuntimeException {

    public UsuarioJaVinculadoException(Long idUsuario) {
        super(
                "Usuário já vinculado a um profissional: "
                        + idUsuario
        );
    }
}