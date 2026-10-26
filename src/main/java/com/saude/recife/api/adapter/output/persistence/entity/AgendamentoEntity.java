package com.saude.recife.api.adapter.output.persistence.entity;

import com.saude.recife.api.domain.model.StatusAgendamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "agendamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_agendamento")
    private Long id;

    @Column(name = "id_paciente", nullable = false)
    private Long idPaciente;

    @Column(name = "id_profissional", nullable = false)
    private Long idProfissional;

    @Column(name = "id_clinica", nullable = false)
    private Long idClinica;

    @Column(name = "id_especialidade", nullable = false)
    private Long idEspecialidade;

    @Column(name = "id_grade")
    private Long idGrade;

    @Column(name = "inicio", nullable = false)
    private OffsetDateTime inicio;

    @Column(name = "fim", nullable = false)
    private OffsetDateTime fim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusAgendamento status;

    @Column(
            name = "codigo_confirmacao",
            nullable = false,
            length = 12
    )
    private String codigoConfirmacao;

    @Column(name = "criado_em", nullable = false)
    private OffsetDateTime criadoEm;

    @Column(name = "cancelado_em")
    private OffsetDateTime canceladoEm;

    @Column(
            name = "motivo_cancelamento",
            length = 500
    )
    private String motivoCancelamento;
}