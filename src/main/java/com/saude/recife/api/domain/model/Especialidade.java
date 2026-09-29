package com.saude.recife.api.domain.model;

public class Especialidade {

    private Long id;
    private String nome;
    private String descricao;
    private boolean ativo;

    public Especialidade(
            Long id,
            String nome,
            String descricao,
            boolean ativo) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }
}