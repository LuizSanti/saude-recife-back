package com.saude.recife.api.application.port.input;

public interface AutenticarUsuarioUseCase {

    ResultadoAutenticacao autenticar(String email, String senha);
}
