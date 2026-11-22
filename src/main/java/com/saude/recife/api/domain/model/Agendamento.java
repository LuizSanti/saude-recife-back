package com.saude.recife.api.domain.model;

import com.saude.recife.api.domain.exception.AgendamentoTransicaoInvalidaException;

import java.time.OffsetDateTime;

public class Agendamento {

    private Long id;

    private Long idPaciente;
    private Long idProfissional;
    private Long idClinica;
    private Long idEspecialidade;
    private Long idGrade;

    private OffsetDateTime inicio;
    private OffsetDateTime fim;

    private StatusAgendamento status;

    private String codigoConfirmacao;

    private OffsetDateTime criadoEm;
    private OffsetDateTime canceladoEm;

    private String motivoCancelamento;

    public Agendamento(
            Long id,
            Long idPaciente,
            Long idProfissional,
            Long idClinica,
            Long idEspecialidade,
            Long idGrade,
            OffsetDateTime inicio,
            OffsetDateTime fim,
            StatusAgendamento status,
            String codigoConfirmacao,
            OffsetDateTime criadoEm,
            OffsetDateTime canceladoEm,
            String motivoCancelamento) {

        this.id = id;
        this.idPaciente = idPaciente;
        this.idProfissional = idProfissional;
        this.idClinica = idClinica;
        this.idEspecialidade = idEspecialidade;
        this.idGrade = idGrade;
        this.inicio = inicio;
        this.fim = fim;
        this.status = status;
        this.codigoConfirmacao = codigoConfirmacao;
        this.criadoEm = criadoEm;
        this.canceladoEm = canceladoEm;
        this.motivoCancelamento = motivoCancelamento;
    }

    public void cancelar(OffsetDateTime dataCancelamento) {

        if (status == StatusAgendamento.CANCELADO) {
            return;
        }

        this.status = StatusAgendamento.CANCELADO;
        this.canceladoEm = dataCancelamento;
    }

    public void cancelarPorProfissional(
            OffsetDateTime dataCancelamento,
            String motivo) {

        if (status != StatusAgendamento.CONFIRMADO) {
            throw new AgendamentoTransicaoInvalidaException(
                    "Somente consultas confirmadas podem ser desmarcadas."
            );
        }

        if (!inicio.isAfter(dataCancelamento)) {
            throw new AgendamentoTransicaoInvalidaException(
                    "Consultas iniciadas não podem ser desmarcadas."
            );
        }

        if (motivo == null || motivo.isBlank()) {
            throw new AgendamentoTransicaoInvalidaException(
                    "Informe o motivo do cancelamento."
            );
        }

        this.status = StatusAgendamento.CANCELADO;
        this.canceladoEm = dataCancelamento;
        this.motivoCancelamento = motivo.trim();
    }

    public void marcarRealizado(OffsetDateTime momento) {
        if (status != StatusAgendamento.CONFIRMADO) {
            throw new AgendamentoTransicaoInvalidaException(
                    "Somente consultas confirmadas podem ser marcadas como realizadas."
            );
        }

        if (fim.isAfter(momento)) {
            throw new AgendamentoTransicaoInvalidaException(
                    "A consulta só pode ser concluída após o horário final."
            );
        }

        this.status = StatusAgendamento.REALIZADO;
    }

    public Long getId() {
        return id;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public Long getIdProfissional() {
        return idProfissional;
    }

    public Long getIdClinica() {
        return idClinica;
    }

    public Long getIdEspecialidade() {
        return idEspecialidade;
    }

    public Long getIdGrade() {
        return idGrade;
    }

    public OffsetDateTime getInicio() {
        return inicio;
    }

    public OffsetDateTime getFim() {
        return fim;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public String getCodigoConfirmacao() {
        return codigoConfirmacao;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public OffsetDateTime getCanceladoEm() {
        return canceladoEm;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }
}