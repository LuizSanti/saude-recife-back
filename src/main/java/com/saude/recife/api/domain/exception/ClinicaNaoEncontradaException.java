package com.saude.recife.api.domain.exception;

public class ClinicaNaoEncontradaException extends RuntimeException {

    public ClinicaNaoEncontradaException(Long id) {
        super("Clínica não encontrada com id: " + id);
    }
}