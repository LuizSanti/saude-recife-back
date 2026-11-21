package com.saude.recife.api.adapter.output.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilidade_exame")
@Getter
@Setter
@NoArgsConstructor
public class DisponibilidadeExameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_disponibilidade_exame")
    private Long id;

    @Column(name = "id_exame", nullable = false)
    private Long exameId;

    @Column(name = "id_clinica", nullable = false)
    private Long clinicaId;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;
}
