package com.saude.recife.api.domain.exception;

public class PacienteNaoEncontradoException extends RuntimeException {

    public PacienteNaoEncontradoException(Long id) {
        super("Paciente não encontrado com id: " + id);
    }
}