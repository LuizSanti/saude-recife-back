package com.saude.recife.api.domain.exception;

public class DisponibilidadeNaoEncontradaException
        extends RuntimeException {

    public DisponibilidadeNaoEncontradaException(Long id) {

        super(
                "Disponibilidade não encontrada com id: "
                        + id
        );
    }
}