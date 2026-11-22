package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.output.AgendamentoPort;
import com.saude.recife.api.application.port.output.ClinicaPort;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.application.port.output.EspecialidadePort;
import com.saude.recife.api.application.port.output.ProfissionalPort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.domain.exception.DisponibilidadeInvalidaException;
import com.saude.recife.api.domain.model.Clinica;
import com.saude.recife.api.domain.model.Disponibilidade;
import com.saude.recife.api.domain.model.Especialidade;
import com.saude.recife.api.domain.model.Profissional;
import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DisponibilidadeServiceTest {

    @Test
    void omitsAvailabilityOverlappingConfirmedAppointment() {
        DisponibilidadePort disponibilidadePort = mock(DisponibilidadePort.class);
        AgendamentoPort agendamentoPort = mock(AgendamentoPort.class);
        ZoneId zoneId = ZoneId.of("America/Recife");
        LocalDate data = LocalDate.now(zoneId).plusDays(1);
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
                mock(ProfissionalPort.class),
                mock(UsuarioPort.class),
                mock(ClinicaPort.class),
                mock(EspecialidadePort.class),
                zoneId
        );

        assertEquals(
                List.of(livre),
                service.consultar(data, 1L, 11L, null)
        );
    }

    @Test
    void splitsAvailabilityPeriodIntoThirtyMinuteAppointments() {
        Fixture fixture = fixture();
        LocalDate data = LocalDate.now(fixture.zoneId()).plusDays(1);
        when(fixture.disponibilidadePort().existeSobreposicao(
                1L, data, LocalTime.of(7, 0), LocalTime.of(12, 0)
        )).thenReturn(false);
        when(fixture.agendamentoPort().existeConflitoProfissional(
                eq(1L),
                eq(data.atTime(7, 0).atZone(fixture.zoneId()).toOffsetDateTime()),
                eq(data.atTime(12, 0).atZone(fixture.zoneId()).toOffsetDateTime())
        )).thenReturn(false);
        when(fixture.disponibilidadePort().salvarTodas(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<Disponibilidade> result = fixture.service().criarParaProfissional(
                2L,
                3L,
                data,
                LocalTime.of(7, 0),
                LocalTime.of(12, 0),
                "medico@example.test"
        );

        assertEquals(10, result.size());
        assertEquals(LocalTime.of(7, 0), result.get(0).getHoraInicio());
        assertEquals(LocalTime.of(7, 30), result.get(0).getHoraFim());
        assertEquals(LocalTime.of(11, 30), result.get(9).getHoraInicio());
        assertEquals(LocalTime.of(12, 0), result.get(9).getHoraFim());
    }

    @Test
    void rejectsAvailabilityPeriodNotDivisibleByThirtyMinutes() {
        Fixture fixture = fixture();

        assertThrows(
                DisponibilidadeInvalidaException.class,
                () -> fixture.service().criarParaProfissional(
                        2L,
                        3L,
                        LocalDate.now(fixture.zoneId()).plusDays(1),
                        LocalTime.of(7, 0),
                        LocalTime.of(12, 15),
                        "medico@example.test"
                )
        );

        verify(fixture.disponibilidadePort(), org.mockito.Mockito.never())
                .salvarTodas(anyList());
    }

    @Test
    void rejectsAvailabilityForUnassociatedClinicSpecialtyOrProfessional() {
        Fixture fixture = fixture();
        when(fixture.profissionalPort().estaVinculadoAClinica(1L, 2L))
                .thenReturn(false);

        assertThrows(
                DisponibilidadeInvalidaException.class,
                () -> fixture.service().criarParaProfissional(
                        2L,
                        3L,
                        LocalDate.now(fixture.zoneId()).plusDays(1),
                        LocalTime.of(7, 0),
                        LocalTime.of(12, 0),
                        "medico@example.test"
                )
        );

        verify(fixture.disponibilidadePort(), org.mockito.Mockito.never())
                .salvarTodas(anyList());
    }

    private Fixture fixture() {
        DisponibilidadePort disponibilidadePort = mock(DisponibilidadePort.class);
        AgendamentoPort agendamentoPort = mock(AgendamentoPort.class);
        ProfissionalPort profissionalPort = mock(ProfissionalPort.class);
        UsuarioPort usuarioPort = mock(UsuarioPort.class);
        ClinicaPort clinicaPort = mock(ClinicaPort.class);
        EspecialidadePort especialidadePort = mock(EspecialidadePort.class);
        ZoneId zoneId = ZoneId.of("America/Recife");

        when(usuarioPort.buscarPorEmail("medico@example.test"))
                .thenReturn(Optional.of(new Usuario(
                        10L,
                        "Médico",
                        "medico@example.test",
                        "hash",
                        null,
                        TipoUsuario.PROFISSIONAL,
                        true,
                        LocalDateTime.now()
                )));
        when(profissionalPort.buscarPorUsuario(10L))
                .thenReturn(Optional.of(new Profissional(
                        1L, "123", "CRM", "123", "PE", true, 10L
                )));
        when(clinicaPort.buscarPorId(2L))
                .thenReturn(Optional.of(new Clinica(
                        2L, "Clínica", null, null, null, null, null, null, null, null, true
                )));
        when(especialidadePort.buscarPorId(3L))
                .thenReturn(Optional.of(new Especialidade(
                        3L, "Cardiologia", null, true
                )));
        when(profissionalPort.estaVinculadoAClinica(1L, 2L))
                .thenReturn(true);
        when(profissionalPort.possuiEspecialidade(1L, 3L))
                .thenReturn(true);
        when(clinicaPort.ofereceEspecialidade(2L, 3L))
                .thenReturn(true);

        return new Fixture(
                new DisponibilidadeService(
                        disponibilidadePort,
                        agendamentoPort,
                        profissionalPort,
                        usuarioPort,
                        clinicaPort,
                        especialidadePort,
                        zoneId
                ),
                disponibilidadePort,
                agendamentoPort,
                profissionalPort,
                zoneId
        );
    }

    private record Fixture(
            DisponibilidadeService service,
            DisponibilidadePort disponibilidadePort,
            AgendamentoPort agendamentoPort,
            ProfissionalPort profissionalPort,
            ZoneId zoneId
    ) {
    }
}
