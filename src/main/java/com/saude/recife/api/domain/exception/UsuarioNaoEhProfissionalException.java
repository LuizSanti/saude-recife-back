package com.saude.recife.api.domain.exception;

public class UsuarioNaoEhProfissionalException
        extends RuntimeException {

    public UsuarioNaoEhProfissionalException(Long idUsuario) {
        super(
                "O usuário informado não possui tipo PROFISSIONAL: "
                        + idUsuario
        );
    }
}