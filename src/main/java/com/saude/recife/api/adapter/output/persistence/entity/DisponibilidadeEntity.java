package com.saude.recife.api.adapter.output.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilidade")
public class DisponibilidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disponibilidade")
    private Long id;

    @Column(name = "id_profissional", nullable = false)
    private Long idProfissional;

    @Column(name = "id_clinica", nullable = false)
    private Long idClinica;

    @Column(name = "id_especialidade", nullable = false)
    private Long idEspecialidade;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    public DisponibilidadeEntity() {
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