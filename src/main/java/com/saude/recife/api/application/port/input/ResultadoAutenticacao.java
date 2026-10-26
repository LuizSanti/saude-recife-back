package com.saude.recife.api.application.port.input;

public class ResultadoAutenticacao {

    private final String token;
    private final String tipoUsuario;
    private final Long id;
    private final String nome;
    private final String email;

    public ResultadoAutenticacao(String token, String tipoUsuario,
                                 Long id, String nome, String email) {
        this.token = token;
        this.tipoUsuario = tipoUsuario;
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public String getToken() { return token; }
    public String getTipoUsuario() { return tipoUsuario; }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
}
