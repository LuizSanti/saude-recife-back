package com.saude.recife.api.application.port.input;

public class ResultadoAutenticacao {

    private final String token;
    private final String tipoUsuario;

    public ResultadoAutenticacao(String token, String tipoUsuario) {
        this.token = token;
        this.tipoUsuario = tipoUsuario;
    }

    public String getToken() {
        return token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}
