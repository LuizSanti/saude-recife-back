package com.saude.recife.api.adapter.output.persistence.entity;

import com.saude.recife.api.domain.model.StatusAgendamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "agendamento_exame")
@Getter
@Setter
@NoArgsConstructor
public class AgendamentoExameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento_exame")
    private Long id;

    @Column(name = "id_paciente", nullable = false)
    private Long pacienteId;

    @Column(name = "id_disponibilidade_exame", nullable = false)
    private Long disponibilidadeId;

    @Column(name = "id_exame", nullable = false)
    private Long exameId;

    @Column(name = "id_clinica", nullable = false)
    private Long clinicaId;

    @Column(name = "inicio", nullable = false)
    private OffsetDateTime inicio;

    @Column(name = "fim", nullable = false)
    private OffsetDateTime fim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusAgendamento status;

    @Column(name = "codigo_confirmacao", nullable = false, length = 12)
    private String codigoConfirmacao;

    @Column(name = "criado_em", nullable = false)
    private OffsetDateTime criadoEm;

    @Column(name = "cancelado_em")
    private OffsetDateTime canceladoEm;
}
