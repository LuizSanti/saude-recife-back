package com.saude.recife.api.domain.model;

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