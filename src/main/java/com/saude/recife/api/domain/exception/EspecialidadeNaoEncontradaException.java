package com.saude.recife.api.domain.exception;

public class EspecialidadeNaoEncontradaException extends RuntimeException {
    public EspecialidadeNaoEncontradaException(Long id) {

        super("Especialidade não encontrada com id: " + id);
    }
}
