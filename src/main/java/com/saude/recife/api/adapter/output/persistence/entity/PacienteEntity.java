package com.saude.recife.api.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Long id;

    @Column(
            name = "cpf",
            nullable = false,
            unique = true,
            length = 14
    )
    private String cpf;

    @Column(
            name = "data_nascimento",
            nullable = false
    )
    private LocalDate dataNascimento;

    @Column(
            name = "sexo",
            length = 30
    )
    private String sexo;

    @Column(
            name = "observacoes",
            columnDefinition = "TEXT"
    )
    private String observacoes;

    @Column(
            name = "id_usuario",
            nullable = false,
            unique = true
    )
    private Long idUsuario;
}