package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioPort {

    Usuario salvar(Usuario usuario);

    Usuario atualizar(Usuario usuario);

    void desativar(Long id);

    Optional<Usuario> buscarPorEmail(String email);

    Optional<Usuario> buscarPorId(Long id);

    List<Usuario> listarTodos();

    boolean existePorEmail(String email);
}