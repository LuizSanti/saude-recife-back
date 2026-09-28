package com.saude.recife.api.domain.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(Long id) {
        super("Usuario nao encontrado com id: " + id);
    }
}