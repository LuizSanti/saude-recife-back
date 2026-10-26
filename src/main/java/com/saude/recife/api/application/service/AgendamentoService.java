package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.AgendamentoUseCase;
import com.saude.recife.api.application.port.output.AgendamentoPort;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.application.port.output.PacientePort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.domain.exception.*;
import com.saude.recife.api.domain.model.*;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

public class AgendamentoService
        implements AgendamentoUseCase {

    private final AgendamentoPort agendamentoPort;
    private final DisponibilidadePort disponibilidadePort;
    private final UsuarioPort usuarioPort;
    private final PacientePort pacientePort;

    private final ZoneId zoneId;

    public AgendamentoService(
            AgendamentoPort agendamentoPort,
            DisponibilidadePort disponibilidadePort,
            UsuarioPort usuarioPort,
            PacientePort pacientePort,
            ZoneId zoneId) {

        this.agendamentoPort = agendamentoPort;
        this.disponibilidadePort = disponibilidadePort;
        this.usuarioPort = usuarioPort;
        this.pacientePort = pacientePort;
        this.zoneId = zoneId;
    }

    @Override
    public Agendamento agendar(
            Long idDisponibilidade,
            String emailUsuario) {

        Paciente paciente =
                obterPacienteAutenticado(emailUsuario);

        Disponibilidade disponibilidade =
                disponibilidadePort
                        .buscarPorId(idDisponibilidade)
                        .orElseThrow(
                                () ->
                                        new DisponibilidadeNaoEncontradaException(
                                                idDisponibilidade
                                        )
                        );

        if (!disponibilidade.isAtivo()) {
            throw new HorarioIndisponivelException();
        }

        OffsetDateTime inicio =
                disponibilidade
                        .getData()
                        .atTime(disponibilidade.getHoraInicio())
                        .atZone(zoneId)
                        .toOffsetDateTime();

        OffsetDateTime fim =
                disponibilidade
                        .getData()
                        .atTime(disponibilidade.getHoraFim())
                        .atZone(zoneId)
                        .toOffsetDateTime();

        OffsetDateTime agora =
                OffsetDateTime.now(zoneId);

        if (!inicio.isAfter(agora)) {
            throw new HorarioIndisponivelException();
        }

        boolean existeConflito =
                agendamentoPort
                        .existeConflitoProfissional(
                                disponibilidade.getIdProfissional(),
                                inicio,
                                fim
                        );

        if (existeConflito) {
            throw new HorarioIndisponivelException();
        }

        Agendamento agendamento =
                new Agendamento(
                        null,
                        paciente.getId(),
                        disponibilidade.getIdProfissional(),
                        disponibilidade.getIdClinica(),
                        disponibilidade.getIdEspecialidade(),

                        null,

                        inicio,
                        fim,

                        StatusAgendamento.CONFIRMADO,

                        gerarCodigoConfirmacao(),

                        OffsetDateTime.now(zoneId),

                        null,
                        null
                );

        return agendamentoPort.salvar(agendamento);
    }

    @Override
    public Agendamento cancelar(
            Long idAgendamento,
            String emailUsuario) {

        Paciente paciente =
                obterPacienteAutenticado(emailUsuario);

        Agendamento agendamento =
                agendamentoPort
                        .buscarPorIdEPaciente(
                                idAgendamento,
                                paciente.getId()
                        )
                        .orElseThrow(
                                () ->
                                        new AgendamentoNaoEncontradoException(
                                                idAgendamento
                                        )
                        );

        agendamento.cancelar(
                OffsetDateTime.now(zoneId)
        );

        return agendamentoPort.salvar(
                agendamento
        );
    }

    @Override
    public List<Agendamento> listarMeus(
            String emailUsuario) {

        Paciente paciente =
                obterPacienteAutenticado(emailUsuario);

        return agendamentoPort
                .listarPorPaciente(
                        paciente.getId()
                );
    }

    private Paciente obterPacienteAutenticado(
            String emailUsuario) {

        Usuario usuario =
                usuarioPort
                        .buscarPorEmail(emailUsuario)
                        .orElseThrow(
                                () ->
                                        new UsuarioNaoEncontradoException(
                                                emailUsuario
                                        )
                        );

        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }

        if (usuario.getTipoUsuario()
                != TipoUsuario.PACIENTE) {

            throw new UsuarioNaoEhPacienteException(
                    usuario.getId()
            );
        }

        return pacientePort
                .buscarPorUsuario(usuario.getId())
                .orElseThrow(
                        () ->
                                new PacienteNaoEncontradoParaUsuarioException(
                                        usuario.getId()
                                )
                );
    }

    private String gerarCodigoConfirmacao() {

        return UUID
                .randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}