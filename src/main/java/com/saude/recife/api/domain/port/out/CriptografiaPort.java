package com.saude.recife.api.domain.port.out;

public interface CriptografiaPort {

    String criptografar(String senhaTexto);

    boolean verificar(String senhaTexto, String senhaHash);
}