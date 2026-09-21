package com.saude.recife.api.application.port.output;

public interface CriptografiaPort {

    String criptografar(String senhaTexto);

    boolean verificar(String senhaTexto, String senhaHash);
}