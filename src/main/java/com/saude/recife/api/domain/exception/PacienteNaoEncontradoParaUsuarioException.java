package com.saude.recife.api.domain.exception;

public class PacienteNaoEncontradoParaUsuarioException
        extends RuntimeException {

    public PacienteNaoEncontradoParaUsuarioException(
            Long idUsuario) {

        super(
                "Não foi encontrado paciente vinculado ao usuário: "
                        + idUsuario
        );
    }
}