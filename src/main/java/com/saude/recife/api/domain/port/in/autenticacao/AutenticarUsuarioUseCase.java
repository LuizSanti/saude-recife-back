package com.saude.recife.api.domain.port.in.autenticacao;

public interface AutenticarUsuarioUseCase {

    ResultadoAutenticacao autenticar(String email, String senha);
}
