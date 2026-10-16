package com.saude.recife.api.domain.model;

public class Clinica {

    private Long id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private boolean ativo;

    public Clinica(
            Long id,
            String nome,
            String cnpj,
            String telefone,
            String email,
            String logradouro,
            String numero,
            String bairro,
            String cidade,
            String uf,
            boolean ativo) {

        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
