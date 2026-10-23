package com.saude.recife.api.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilidade {
    private final Long id;
    private final Long idProfissional;
    private final Long idClinica;
    private final Long idEspecialidade;
    private final LocalDate data;
    private final LocalTime horaInicio;
    private final LocalTime horaFim;
    private final boolean ativo;

    public Disponibilidade(
            Long id,
            Long idProfissional,
            Long idClinica,
            Long idEspecialidade,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim,
            boolean ativo) {

        this.id = id;
        this.idProfissional = idProfissional;
        this.idClinica = idClinica;
        this.idEspecialidade = idEspecialidade;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
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

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
