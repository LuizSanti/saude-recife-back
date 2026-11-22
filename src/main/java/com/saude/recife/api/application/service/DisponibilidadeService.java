package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.DisponibilidadeUseCase;
import com.saude.recife.api.application.port.output.AgendamentoPort;
import com.saude.recife.api.application.port.output.ClinicaPort;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.application.port.output.EspecialidadePort;
import com.saude.recife.api.application.port.output.ProfissionalPort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.domain.exception.ClinicaNaoEncontradaException;
import com.saude.recife.api.domain.exception.DisponibilidadeInvalidaException;
import com.saude.recife.api.domain.exception.DisponibilidadeNaoEncontradaException;
import com.saude.recife.api.domain.exception.EspecialidadeNaoEncontradaException;
import com.saude.recife.api.domain.exception.ProfissionalNaoEncontradoParaUsuarioException;
import com.saude.recife.api.domain.exception.UsuarioInativoException;
import com.saude.recife.api.domain.exception.UsuarioNaoEhProfissionalException;
import com.saude.recife.api.domain.exception.UsuarioNaoEncontradoException;
import com.saude.recife.api.domain.model.Disponibilidade;
import com.saude.recife.api.domain.model.Profissional;
import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;

import java.time.LocalDate;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class DisponibilidadeService
        implements DisponibilidadeUseCase {

    private static final long DURACAO_CONSULTA_MINUTOS = 30;

    private final DisponibilidadePort disponibilidadePort;
    private final AgendamentoPort agendamentoPort;
    private final ProfissionalPort profissionalPort;
    private final UsuarioPort usuarioPort;
    private final ClinicaPort clinicaPort;
    private final EspecialidadePort especialidadePort;
    private final ZoneId zoneId;

    public DisponibilidadeService(
            DisponibilidadePort disponibilidadePort,
            AgendamentoPort agendamentoPort,
            ProfissionalPort profissionalPort,
            UsuarioPort usuarioPort,
            ClinicaPort clinicaPort,
            EspecialidadePort especialidadePort,
            ZoneId zoneId) {

        this.disponibilidadePort = disponibilidadePort;
        this.agendamentoPort = agendamentoPort;
        this.profissionalPort = profissionalPort;
        this.usuarioPort = usuarioPort;
        this.clinicaPort = clinicaPort;
        this.especialidadePort = especialidadePort;
        this.zoneId = zoneId;
    }

    @Override
    public List<Disponibilidade> consultar(
            LocalDate data,
            Long clinicaId,
            Long especialidadeId,
            Long profissionalId) {

        return disponibilidadePort.consultar(
                        data,
                        clinicaId,
                        especialidadeId,
                        profissionalId
                )
                .stream()
                .filter(disponibilidade -> !agendamentoPort.existeConflitoProfissional(
                        disponibilidade.getIdProfissional(),
                        disponibilidade.getData()
                                .atTime(disponibilidade.getHoraInicio())
                                .atZone(zoneId)
                                .toOffsetDateTime(),
                        disponibilidade.getData()
                                .atTime(disponibilidade.getHoraFim())
                                .atZone(zoneId)
                                .toOffsetDateTime()
                ))
                .toList();
    }

    @Override
    public List<Disponibilidade> listarDoProfissional(String emailUsuario) {
        Profissional profissional = obterProfissional(emailUsuario);
        return disponibilidadePort.listarPorProfissional(profissional.getId());
    }

    @Override
    public List<Disponibilidade> criarParaProfissional(
            Long clinicaId,
            Long especialidadeId,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim,
            String emailUsuario) {

        Profissional profissional = obterProfissional(emailUsuario);
        if (clinicaId == null || clinicaPort.buscarPorId(clinicaId)
                .filter(clinica -> clinica.isAtivo())
                .isEmpty()) {
            throw new ClinicaNaoEncontradaException(clinicaId);
        }
        if (especialidadeId == null || especialidadePort.buscarPorId(especialidadeId)
                .filter(especialidade -> especialidade.isAtivo())
                .isEmpty()) {
            throw new EspecialidadeNaoEncontradaException(especialidadeId);
        }
        if (!profissionalPort.estaVinculadoAClinica(
                    profissional.getId(), clinicaId)
                || !profissionalPort.possuiEspecialidade(
                    profissional.getId(), especialidadeId)
                || !clinicaPort.ofereceEspecialidade(
                    clinicaId, especialidadeId)) {
            throw new DisponibilidadeInvalidaException(
                    "O profissional, a clínica e a especialidade precisam estar vinculados antes de cadastrar horários."
            );
        }
        if (data == null || horaInicio == null || horaFim == null
                || !horaFim.isAfter(horaInicio)
                || Duration.between(horaInicio, horaFim).toMinutes()
                % DURACAO_CONSULTA_MINUTOS != 0
                || !data.atTime(horaInicio).atZone(zoneId).isAfter(java.time.ZonedDateTime.now(zoneId))) {
            throw new DisponibilidadeInvalidaException(
                    "Informe uma data futura e um período com duração múltipla de 30 minutos."
            );
        }
        if (disponibilidadePort.existeSobreposicao(
                profissional.getId(), data, horaInicio, horaFim)
                || agendamentoPort.existeConflitoProfissional(
                profissional.getId(),
                data.atTime(horaInicio).atZone(zoneId).toOffsetDateTime(),
                data.atTime(horaFim).atZone(zoneId).toOffsetDateTime())) {
            throw new DisponibilidadeInvalidaException(
                    "Este horário conflita com outra disponibilidade ou consulta."
            );
        }

        List<Disponibilidade> horarios = new ArrayList<>();
        LocalTime inicioHorario = horaInicio;
        while (inicioHorario.isBefore(horaFim)) {
            LocalTime fimHorario = inicioHorario.plusMinutes(DURACAO_CONSULTA_MINUTOS);
            horarios.add(new Disponibilidade(
                    null,
                    profissional.getId(),
                    clinicaId,
                    especialidadeId,
                    data,
                    inicioHorario,
                    fimHorario,
                    true
            ));
            inicioHorario = fimHorario;
        }

        return disponibilidadePort.salvarTodas(horarios);
    }

    @Override
    public void inativarDoProfissional(Long id, String emailUsuario) {
        Profissional profissional = obterProfissional(emailUsuario);
        Disponibilidade existente = disponibilidadePort
                .buscarPorIdEProfissional(id, profissional.getId())
                .orElseThrow(() -> new DisponibilidadeNaoEncontradaException(id));
        if (existente.isAtivo()) {
            disponibilidadePort.salvar(new Disponibilidade(
                    existente.getId(),
                    existente.getIdProfissional(),
                    existente.getIdClinica(),
                    existente.getIdEspecialidade(),
                    existente.getData(),
                    existente.getHoraInicio(),
                    existente.getHoraFim(),
                    false
            ));
        }
    }

    private Profissional obterProfissional(String emailUsuario) {
        Usuario usuario = usuarioPort.buscarPorEmail(emailUsuario)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(emailUsuario));
        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }
        if (usuario.getTipoUsuario() != TipoUsuario.PROFISSIONAL) {
            throw new UsuarioNaoEhProfissionalException(usuario.getId());
        }
        return profissionalPort.buscarPorUsuario(usuario.getId())
                .filter(Profissional::isAtivo)
                .orElseThrow(() -> new ProfissionalNaoEncontradoParaUsuarioException(usuario.getId()));
    }
}