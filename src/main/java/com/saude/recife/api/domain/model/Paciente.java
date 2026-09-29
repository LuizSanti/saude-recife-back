package com.saude.recife.api.domain.model;

import java.time.LocalDate;

public class Paciente {

    private Long id;
    private String cpf;
    private LocalDate dataNascimento;
    private String sexo;
    private String observacoes;
    private Long idUsuario;

    public Paciente(
            Long id,
            String cpf,
            LocalDate dataNascimento,
            String sexo,
            String observacoes,
            Long idUsuario) {

        this.id = id;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.observacoes = observacoes;
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }
}