package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.output.AgendamentoPort;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.domain.model.Disponibilidade;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DisponibilidadeServiceTest {

    @Test
    void omitsAvailabilityOverlappingConfirmedAppointment() {
        DisponibilidadePort disponibilidadePort = mock(DisponibilidadePort.class);
        AgendamentoPort agendamentoPort = mock(AgendamentoPort.class);
        ZoneId zoneId = ZoneId.of("America/Recife");
        LocalDate data = LocalDate.of(2026, 11, 23);
        Disponibilidade ocupada = new Disponibilidade(
                1L, 1L, 1L, 11L, data,
                LocalTime.of(9, 0), LocalTime.of(9, 30), true
        );
        Disponibilidade livre = new Disponibilidade(
                2L, 1L, 1L, 11L, data,
                LocalTime.of(10, 0), LocalTime.of(10, 30), true
        );

        when(disponibilidadePort.consultar(data, 1L, 11L, null))
                .thenReturn(List.of(ocupada, livre));
        when(agendamentoPort.existeConflitoProfissional(
                eq(1L),
                eq(data.atTime(9, 0).atZone(zoneId).toOffsetDateTime()),
                eq(data.atTime(9, 30).atZone(zoneId).toOffsetDateTime())
        )).thenReturn(true);
        when(agendamentoPort.existeConflitoProfissional(
                eq(1L),
                eq(data.atTime(10, 0).atZone(zoneId).toOffsetDateTime()),
                eq(data.atTime(10, 30).atZone(zoneId).toOffsetDateTime())
        )).thenReturn(false);

        DisponibilidadeService service = new DisponibilidadeService(
                disponibilidadePort,
                agendamentoPort,
                zoneId
        );

        assertEquals(
                List.of(livre),
                service.consultar(data, 1L, 11L, null)
        );
    }
}
