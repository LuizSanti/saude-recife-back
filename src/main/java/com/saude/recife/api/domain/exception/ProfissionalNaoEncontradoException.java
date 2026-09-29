package com.saude.recife.api.domain.exception;

public class ProfissionalNaoEncontradoException extends RuntimeException {

    public ProfissionalNaoEncontradoException(Long id) {
        super("Profissional não encontrado com id: " + id);
    }
}