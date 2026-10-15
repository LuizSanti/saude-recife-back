package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;

import java.util.List;

public interface UsuarioUseCase {

    Usuario cadastrar(String nome, String email, String senha, String telefone, TipoUsuario tipoUsuario);

    Usuario atualizar(Long id, String nome, String telefone, TipoUsuario tipoUsuario);

    void desativar(Long id);

    Usuario buscarPorId(Long id);

    List<Usuario> listarTodos();
}
