package com.saude.recife.api.domain.exception;

public class UsuarioNaoEhPacienteException extends RuntimeException {

    public UsuarioNaoEhPacienteException(Long idUsuario) {
        super(
                "O usuário informado não possui tipo PACIENTE: "
                        + idUsuario
        );
    }
}