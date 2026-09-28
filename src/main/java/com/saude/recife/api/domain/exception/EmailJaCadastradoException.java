package com.saude.recife.api.domain.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("Ja existe um usuario cadastrado com o email: " + email);
    }
}
