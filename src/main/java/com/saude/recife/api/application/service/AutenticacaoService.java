package com.saude.recife.api.application.service;

import com.saude.recife.api.domain.exception.CredenciaisInvalidasException;
import com.saude.recife.api.domain.exception.UsuarioInativoException;
import com.saude.recife.api.domain.model.Usuario;
import com.saude.recife.api.application.port.input.AutenticarUsuarioUseCase;
import com.saude.recife.api.application.port.input.ResultadoAutenticacao;
import com.saude.recife.api.application.port.output.CriptografiaPort;
import com.saude.recife.api.application.port.output.TokenPort;
import com.saude.recife.api.application.port.output.UsuarioPort;

public class AutenticacaoService implements AutenticarUsuarioUseCase {

    private final UsuarioPort usuarioPort;
    private final CriptografiaPort criptografiaPort;
    private final TokenPort tokenPort;

    public AutenticacaoService(UsuarioPort usuarioPort,
                               CriptografiaPort criptografiaPort,
                               TokenPort tokenPort) {
        this.usuarioPort = usuarioPort;
        this.criptografiaPort = criptografiaPort;
        this.tokenPort = tokenPort;
    }

    @Override
    public ResultadoAutenticacao autenticar(String email, String senha) {
        Usuario usuario = usuarioPort.buscarPorEmail(email)
                .orElseThrow(() -> new CredenciaisInvalidasException());

        boolean senhaValida = criptografiaPort.verificar(senha, usuario.getSenhaHash());

        if (!senhaValida) {
            throw new CredenciaisInvalidasException();
        }

        if (!usuario.isAtivo()) {
            throw new UsuarioInativoException();
        }

        String token = tokenPort.gerar(usuario);

        return new ResultadoAutenticacao(token, usuario.getTipoUsuario().name());
    }
}
