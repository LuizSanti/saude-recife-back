package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.output.ExameAgendamentoPort;
import com.saude.recife.api.application.port.output.PacientePort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.domain.exception.HorarioIndisponivelException;
import com.saude.recife.api.domain.model.AgendamentoExame;
import com.saude.recife.api.domain.model.DisponibilidadeExame;
import com.saude.recife.api.domain.model.Paciente;
import com.saude.recife.api.domain.model.StatusAgendamento;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExameAgendamentoServiceTest {

    private static final ZoneId ZONE_ID = ZoneId.of("America/Recife");

    @Test
    void bookingUsesPatientAndRecifeTimeAndPersistsConfirmation() {
        ExameAgendamentoPort examePort = mock(ExameAgendamentoPort.class);
        UsuarioPort usuarioPort = mock(UsuarioPort.class);
        PacientePort pacientePort = mock(PacientePort.class);
        LocalDate data = LocalDate.now(ZONE_ID).plusDays(1);
        DisponibilidadeExame slot = slot(data);
        Usuario usuario = usuario();
        Paciente paciente = paciente();

        when(usuarioPort.buscarPorEmail(usuario.getEmail()))
                .thenReturn(Optional.of(usuario));
        when(pacientePort.buscarPorUsuario(usuario.getId()))
                .thenReturn(Optional.of(paciente));
        when(examePort.buscarDisponibilidade(slot.id()))
                .thenReturn(Optional.of(slot));
        when(examePort.existeConflito(
                eq(slot.exameId()),
                eq(slot.clinicaId()),
                any(),
                any()
        )).thenReturn(false);
        when(examePort.salvar(any(AgendamentoExame.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ExameAgendamentoService service = new ExameAgendamentoService(
                examePort, usuarioPort, pacientePort, ZONE_ID
        );

        AgendamentoExame agendamento = service.agendar(slot.id(), usuario.getEmail());

        assertEquals(paciente.getId(), agendamento.pacienteId());
        assertEquals(slot.id(), agendamento.disponibilidadeId());
        assertEquals(
                data.atTime(slot.horaInicio()).atZone(ZONE_ID).toOffsetDateTime(),
                agendamento.inicio()
        );
        assertEquals(StatusAgendamento.CONFIRMADO, agendamento.status());
        assertEquals(12, agendamento.codigoConfirmacao().length());
    }

    @Test
    void bookingRejectsAConflictingExamSlot() {
        ExameAgendamentoPort examePort = mock(ExameAgendamentoPort.class);
        UsuarioPort usuarioPort = mock(UsuarioPort.class);
        PacientePort pacientePort = mock(PacientePort.class);
        LocalDate data = LocalDate.now(ZONE_ID).plusDays(1);
        DisponibilidadeExame slot = slot(data);
        Usuario usuario = usuario();

        when(usuarioPort.buscarPorEmail(usuario.getEmail()))
                .thenReturn(Optional.of(usuario));
        when(pacientePort.buscarPorUsuario(usuario.getId()))
                .thenReturn(Optional.of(paciente()));
        when(examePort.buscarDisponibilidade(slot.id()))
                .thenReturn(Optional.of(slot));
        when(examePort.existeConflito(
                eq(slot.exameId()),
                eq(slot.clinicaId()),
                any(),
                any()
        )).thenReturn(true);

        ExameAgendamentoService service = new ExameAgendamentoService(
                examePort, usuarioPort, pacientePort, ZONE_ID
        );

        assertThrows(
                HorarioIndisponivelException.class,
                () -> service.agendar(slot.id(), usuario.getEmail())
        );
        verify(examePort, never()).salvar(any(AgendamentoExame.class));
    }

    @Test
    void availabilityOmitsSlotsWithAnExistingExamConflict() {
        ExameAgendamentoPort examePort = mock(ExameAgendamentoPort.class);
        LocalDate data = LocalDate.now(ZONE_ID).plusDays(1);
        DisponibilidadeExame slot = slot(data);
        when(examePort.consultarDisponibilidades(data, slot.clinicaId(), slot.exameId()))
                .thenReturn(List.of(slot));
        when(examePort.existeConflito(
                eq(slot.exameId()),
                eq(slot.clinicaId()),
                any(),
                any()
        )).thenReturn(true);

        ExameAgendamentoService service = new ExameAgendamentoService(
                examePort,
                mock(UsuarioPort.class),
                mock(PacientePort.class),
                ZONE_ID
        );

        assertTrue(
                service.consultarDisponibilidades(data, slot.clinicaId(), slot.exameId())
                        .isEmpty()
        );
    }

    private DisponibilidadeExame slot(LocalDate data) {
        return new DisponibilidadeExame(
                7L,
                2L,
                3L,
                data,
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                true
        );
    }

    private Usuario usuario() {
        return new Usuario(
                5L,
                "Paciente Teste",
                "paciente@example.com",
                "hash",
                "81999999999",
                TipoUsuario.PACIENTE,
                true,
                LocalDateTime.now()
        );
    }

    private Paciente paciente() {
        return new Paciente(
                9L,
                "12345678901",
                LocalDate.of(1990, 1, 1),
                null,
                null,
                5L
        );
    }
}
