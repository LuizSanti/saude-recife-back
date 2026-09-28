package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.application.port.output.CriptografiaPort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.domain.exception.EmailJaCadastradoException;
import com.saude.recife.api.domain.exception.UsuarioNaoEncontradoException;
import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class UsuarioService implements UsuarioUseCase {

    private final UsuarioPort usuarioPort;
    private final CriptografiaPort criptografiaPort;

    public UsuarioService(UsuarioPort usuarioPort, CriptografiaPort criptografiaPort) {
        this.usuarioPort = usuarioPort;
        this.criptografiaPort = criptografiaPort;
    }

    @Override
    public Usuario cadastrar(String nome, String email, String senha, String telefone, TipoUsuario tipoUsuario) {
        if (usuarioPort.existePorEmail(email)) {
            throw new EmailJaCadastradoException(email);
        }

        String senhaHash = criptografiaPort.criptografar(senha);

        Usuario usuario = new Usuario(null, nome, email, senhaHash, telefone, tipoUsuario, true, LocalDateTime.now());

        return usuarioPort.salvar(usuario);
    }

    @Override
    public Usuario atualizar(Long id, String nome, String telefone, TipoUsuario tipoUsuario) {
        Usuario usuario = usuarioPort.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        usuario.atualizarDados(nome, telefone, tipoUsuario);

        return usuarioPort.atualizar(usuario);
    }

    @Override
    public void desativar(Long id) {
        usuarioPort.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        usuarioPort.desativar(id);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioPort.buscarPorId(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioPort.listarTodos();
    }
}
