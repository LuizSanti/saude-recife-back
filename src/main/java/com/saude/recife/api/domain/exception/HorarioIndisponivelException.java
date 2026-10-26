package com.saude.recife.api.domain.exception;

public class HorarioIndisponivelException
        extends RuntimeException {

    public HorarioIndisponivelException() {

        super(
                "Este horário não está disponível para agendamento."
        );
    }
}