package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.AgendamentoUseCase;
import com.saude.recife.api.application.port.output.AgendamentoPort;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.application.port.output.PacientePort;
import com.saude.recife.api.application.port.output.ProfissionalPort;
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
    private final ProfissionalPort profissionalPort;

    private final ZoneId zoneId;

    public AgendamentoService(
            AgendamentoPort agendamentoPort,
            DisponibilidadePort disponibilidadePort,
            UsuarioPort usuarioPort,
            PacientePort pacientePort,
            ProfissionalPort profissionalPort,
            ZoneId zoneId) {

        this.agendamentoPort = agendamentoPort;
        this.disponibilidadePort = disponibilidadePort;
        this.usuarioPort = usuarioPort;
        this.pacientePort = pacientePort;
        this.profissionalPort = profissionalPort;
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
        if (!disponibilidadePort.possuiAssociacoesValidas(idDisponibilidade)) {
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

    @Override
    public List<Agendamento> listarAgendaProfissional(String emailUsuario) {
        Profissional profissional = obterProfissionalAutenticado(emailUsuario);
        return agendamentoPort.listarPorProfissional(profissional.getId());
    }

    @Override
    public Agendamento alterarStatusPorProfissional(
            Long idAgendamento,
            StatusAgendamento status,
            String motivoCancelamento,
            String emailUsuario) {

        Profissional profissional = obterProfissionalAutenticado(emailUsuario);
        Agendamento agendamento = agendamentoPort
                .buscarPorIdEProfissional(idAgendamento, profissional.getId())
                .orElseThrow(() -> new AgendamentoNaoEncontradoException(idAgendamento));

        OffsetDateTime agora = OffsetDateTime.now(zoneId);
        if (status == StatusAgendamento.CANCELADO) {
            agendamento.cancelarPorProfissional(agora, motivoCancelamento);
        } else if (status == StatusAgendamento.REALIZADO) {
            agendamento.marcarRealizado(agora);
        } else {
            throw new AgendamentoTransicaoInvalidaException(
                    "O profissional só pode desmarcar ou concluir uma consulta."
            );
        }

        return agendamentoPort.salvar(agendamento);
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
                        () -> new PacienteNaoEncontradoParaUsuarioException(
                                usuario.getId()
                        )
                );
    }

    private Profissional obterProfissionalAutenticado(String emailUsuario) {
        Usuario usuario = usuarioPort
            .buscarPorEmail(emailUsuario)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(emailUsuario));

        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }

        if (usuario.getTipoUsuario() != TipoUsuario.PROFISSIONAL) {
            throw new UsuarioNaoEhProfissionalException(usuario.getId());
        }

        return profissionalPort
            .buscarPorUsuario(usuario.getId())
            .filter(Profissional::isAtivo)
            .orElseThrow(
                    () -> new ProfissionalNaoEncontradoParaUsuarioException(
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