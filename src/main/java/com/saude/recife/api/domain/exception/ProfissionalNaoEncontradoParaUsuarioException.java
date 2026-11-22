package com.saude.recife.api.domain.exception;

public class ProfissionalNaoEncontradoParaUsuarioException extends RuntimeException {
    public ProfissionalNaoEncontradoParaUsuarioException(Long idUsuario) {
        super("Não há cadastro profissional vinculado ao usuário: " + idUsuario);
    }
}
