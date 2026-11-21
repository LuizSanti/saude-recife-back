package com.saude.recife.api.domain.exception;

public class DisponibilidadeExameNaoEncontradaException
        extends RuntimeException {

    public DisponibilidadeExameNaoEncontradaException(Long id) {
        super("Disponibilidade de exame nao encontrada: " + id);
    }
}
