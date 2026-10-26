package com.saude.recife.api.domain.exception;

public class UsuarioNaoEncontradoException
        extends RuntimeException {

    public UsuarioNaoEncontradoException(Long id) {
        super("Usuário não encontrado com id: " + id);
    }

    public UsuarioNaoEncontradoException(String email) {
        super("Usuário não encontrado com email: " + email);
    }
}