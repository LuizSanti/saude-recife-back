package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.output.AgendamentoPort;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.application.port.output.PacientePort;
import com.saude.recife.api.application.port.output.ProfissionalPort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.domain.exception.HorarioIndisponivelException;
import com.saude.recife.api.domain.model.Disponibilidade;
import com.saude.recife.api.domain.model.Paciente;
import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AgendamentoServiceTest {

    @Test
    void rejectsBookingForAvailabilityWithInvalidAssociations() {
        AgendamentoPort agendamentoPort = mock(AgendamentoPort.class);
        DisponibilidadePort disponibilidadePort = mock(DisponibilidadePort.class);
        UsuarioPort usuarioPort = mock(UsuarioPort.class);
        PacientePort pacientePort = mock(PacientePort.class);
        ProfissionalPort profissionalPort = mock(ProfissionalPort.class);
        String email = "paciente@example.test";
        long disponibilidadeId = 42L;
        long usuarioId = 10L;

        when(usuarioPort.buscarPorEmail(email))
                .thenReturn(Optional.of(new Usuario(
                        usuarioId,
                        "Paciente",
                        email,
                        "hash",
                        null,
                        TipoUsuario.PACIENTE,
                        true,
                        LocalDateTime.now()
                )));
        when(pacientePort.buscarPorUsuario(usuarioId))
                .thenReturn(Optional.of(new Paciente(
                        5L,
                        "12345678901",
                        LocalDate.of(1950, 1, 1),
                        "F",
                        null,
                        usuarioId
                )));
        when(disponibilidadePort.buscarPorId(disponibilidadeId))
                .thenReturn(Optional.of(new Disponibilidade(
                        disponibilidadeId,
                        7L,
                        8L,
                        9L,
                        LocalDate.now(ZoneId.of("America/Recife")).plusDays(1),
                        LocalTime.of(9, 0),
                        LocalTime.of(9, 30),
                        true
                )));
        when(disponibilidadePort.possuiAssociacoesValidas(disponibilidadeId))
                .thenReturn(false);

        AgendamentoService service = new AgendamentoService(
                agendamentoPort,
                disponibilidadePort,
                usuarioPort,
                pacientePort,
                profissionalPort,
                ZoneId.of("America/Recife")
        );

        assertThrows(
                HorarioIndisponivelException.class,
                () -> service.agendar(disponibilidadeId, email)
        );
        verify(agendamentoPort, never()).salvar(any());
    }
}
