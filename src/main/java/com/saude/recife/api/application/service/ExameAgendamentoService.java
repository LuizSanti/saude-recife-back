package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.ExameAgendamentoUseCase;
import com.saude.recife.api.application.port.output.ExameAgendamentoPort;
import com.saude.recife.api.application.port.output.PacientePort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.domain.exception.DisponibilidadeExameNaoEncontradaException;
import com.saude.recife.api.domain.exception.HorarioIndisponivelException;
import com.saude.recife.api.domain.exception.PacienteNaoEncontradoParaUsuarioException;
import com.saude.recife.api.domain.exception.UsuarioInativoException;
import com.saude.recife.api.domain.exception.UsuarioNaoEhPacienteException;
import com.saude.recife.api.domain.exception.UsuarioNaoEncontradoException;
import com.saude.recife.api.domain.model.AgendamentoExame;
import com.saude.recife.api.domain.model.DisponibilidadeExame;
import com.saude.recife.api.domain.model.Exame;
import com.saude.recife.api.domain.model.StatusAgendamento;
import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

public class ExameAgendamentoService implements ExameAgendamentoUseCase {

    private final ExameAgendamentoPort exameAgendamentoPort;
    private final UsuarioPort usuarioPort;
    private final PacientePort pacientePort;
    private final ZoneId zoneId;

    public ExameAgendamentoService(
            ExameAgendamentoPort exameAgendamentoPort,
            UsuarioPort usuarioPort,
            PacientePort pacientePort,
            ZoneId zoneId) {
        this.exameAgendamentoPort = exameAgendamentoPort;
        this.usuarioPort = usuarioPort;
        this.pacientePort = pacientePort;
        this.zoneId = zoneId;
    }

    @Override
    public List<Exame> listarExames() {
        return exameAgendamentoPort.listarExamesAtivos();
    }

    @Override
    public List<DisponibilidadeExame> consultarDisponibilidades(
            LocalDate data,
            Long clinicaId,
            Long exameId) {
        return exameAgendamentoPort
                .consultarDisponibilidades(data, clinicaId, exameId)
                .stream()
                .filter(slot -> !existeConflito(slot))
                .toList();
    }

    @Override
    @Transactional
    public AgendamentoExame agendar(
            Long disponibilidadeId,
            String emailUsuario) {
        var paciente = obterPacienteAutenticado(emailUsuario);
        DisponibilidadeExame slot = exameAgendamentoPort
                .buscarDisponibilidade(disponibilidadeId)
                .orElseThrow(() ->
                        new DisponibilidadeExameNaoEncontradaException(disponibilidadeId));

        OffsetDateTime inicio = slot.data()
                .atTime(slot.horaInicio())
                .atZone(zoneId)
                .toOffsetDateTime();
        OffsetDateTime fim = slot.data()
                .atTime(slot.horaFim())
                .atZone(zoneId)
                .toOffsetDateTime();

        if (!slot.ativo()
                || !inicio.isAfter(OffsetDateTime.now(zoneId))
                || existeConflito(slot)) {
            throw new HorarioIndisponivelException();
        }

        return exameAgendamentoPort.salvar(new AgendamentoExame(
                null,
                paciente.getId(),
                slot.id(),
                slot.exameId(),
                slot.clinicaId(),
                inicio,
                fim,
                StatusAgendamento.CONFIRMADO,
                gerarCodigoConfirmacao(),
                OffsetDateTime.now(zoneId)
        ));
    }

    private boolean existeConflito(DisponibilidadeExame slot) {
        OffsetDateTime inicio = slot.data()
                .atTime(slot.horaInicio())
                .atZone(zoneId)
                .toOffsetDateTime();
        OffsetDateTime fim = slot.data()
                .atTime(slot.horaFim())
                .atZone(zoneId)
                .toOffsetDateTime();

        return exameAgendamentoPort.existeConflito(
                slot.exameId(),
                slot.clinicaId(),
                inicio,
                fim
        );
    }

    private com.saude.recife.api.domain.model.Paciente obterPacienteAutenticado(
            String emailUsuario) {
        Usuario usuario = usuarioPort.buscarPorEmail(emailUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(emailUsuario));

        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }

        if (usuario.getTipoUsuario() != TipoUsuario.PACIENTE) {
            throw new UsuarioNaoEhPacienteException(usuario.getId());
        }

        return pacientePort.buscarPorUsuario(usuario.getId())
                .orElseThrow(() ->
                        new PacienteNaoEncontradoParaUsuarioException(usuario.getId()));
    }

    private String gerarCodigoConfirmacao() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
    }
}
