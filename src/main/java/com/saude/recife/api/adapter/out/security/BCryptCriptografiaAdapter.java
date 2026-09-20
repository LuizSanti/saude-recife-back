package com.saude.recife.api.adapter.out.security;

import com.saude.recife.api.domain.port.out.CriptografiaPort;
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