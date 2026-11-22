package com.saude.recife.api.domain.model;

import com.saude.recife.api.domain.exception.AgendamentoTransicaoInvalidaException;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgendamentoTest {
    private static final ZoneId ZONE = ZoneId.of("America/Recife");

    @Test
    void cannotMarkAppointmentRealizedBeforeItsEnd() {
        OffsetDateTime inicio = OffsetDateTime.now(ZONE).plusHours(1);
        Agendamento agendamento = agendamento(inicio, inicio.plusMinutes(30));

        assertThrows(
                AgendamentoTransicaoInvalidaException.class,
                () -> agendamento.marcarRealizado(OffsetDateTime.now(ZONE))
        );
    }

    @Test
    void canMarkAppointmentRealizedAfterItsEnd() {
        OffsetDateTime fim = OffsetDateTime.now(ZONE).minusMinutes(1);
        Agendamento agendamento = agendamento(fim.minusMinutes(30), fim);

        agendamento.marcarRealizado(OffsetDateTime.now(ZONE));

        assertEquals(StatusAgendamento.REALIZADO, agendamento.getStatus());
    }

    @Test
    void professionalCancellationRequiresReasonAndIsRecorded() {
        OffsetDateTime inicio = OffsetDateTime.now(ZONE).plusHours(1);
        Agendamento agendamento = agendamento(inicio, inicio.plusMinutes(30));

        assertThrows(
                AgendamentoTransicaoInvalidaException.class,
                () -> agendamento.cancelarPorProfissional(
                        OffsetDateTime.now(ZONE),
                        " "
                )
        );

        agendamento.cancelarPorProfissional(
                OffsetDateTime.now(ZONE),
                "Imprevisto médico"
        );

        assertEquals(StatusAgendamento.CANCELADO, agendamento.getStatus());
        assertEquals("Imprevisto médico", agendamento.getMotivoCancelamento());
    }

    private Agendamento agendamento(OffsetDateTime inicio, OffsetDateTime fim) {
        return new Agendamento(
                1L,
                1L,
                1L,
                1L,
                1L,
                null,
                inicio,
                fim,
                StatusAgendamento.CONFIRMADO,
                "ABC123",
                OffsetDateTime.now(ZONE),
                null,
                null
        );
    }
}
