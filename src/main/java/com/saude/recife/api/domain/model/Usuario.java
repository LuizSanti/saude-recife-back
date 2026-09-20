package com.saude.recife.api.domain.model;

import java.time.LocalDateTime;

public class Usuario {

    private Long id;
    private String nome;
    private String email;
    private String senhaHash;
    private String telefone;
    private TipoUsuario tipoUsuario;
    private boolean ativo;
    private LocalDateTime criadoEm;

    public Usuario(Long id, String nome, String email, String senhaHash, String telefone, TipoUsuario tipoUsuario, boolean ativo, LocalDateTime criadoEm) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.telefone = telefone;
        this.tipoUsuario = tipoUsuario;
        this.ativo = ativo;
        this.criadoEm = criadoEm;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getSenhaHash() { return senhaHash; }
    public String getTelefone() { return telefone; }
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public boolean isAtivo() { return ativo; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
