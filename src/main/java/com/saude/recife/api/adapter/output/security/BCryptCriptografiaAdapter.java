package com.saude.recife.api.adapter.output.security;

import com.saude.recife.api.application.port.output.CriptografiaPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptCriptografiaAdapter implements CriptografiaPort {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String criptografar(String senhaTexto) {
        return encoder.encode(senhaTexto);
    }

    @Override
    public boolean verificar(String senhaTexto, String senhaHash) {
        return encoder.matches(senhaTexto, senhaHash);
    }
}