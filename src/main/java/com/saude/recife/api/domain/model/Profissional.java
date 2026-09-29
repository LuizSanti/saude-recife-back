package com.saude.recife.api.domain.model;

public class Profissional {

    private Long id;
    private String cpf;
    private String conselho;
    private String registroProfissional;
    private String ufRegistro;
    private boolean ativo;
    private Long idUsuario;

    public Profissional(
            Long id,
            String cpf,
            String conselho,
            String registroProfissional,
            String ufRegistro,
            boolean ativo,
            Long idUsuario) {

        this.id = id;
        this.cpf = cpf;
        this.conselho = conselho;
        this.registroProfissional = registroProfissional;
        this.ufRegistro = ufRegistro;
        this.ativo = ativo;
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getConselho() {
        return conselho;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public String getUfRegistro() {
        return ufRegistro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }
}