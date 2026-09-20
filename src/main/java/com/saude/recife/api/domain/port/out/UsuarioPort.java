package com.saude.recife.api.domain.port.out;

import com.saude.recife.api.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioPort {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    Optional<Usuario> buscarPorId(Long id);

    boolean existePorEmail(String email);
}