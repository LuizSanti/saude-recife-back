package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.ProfissionalUseCase;
import com.saude.recife.api.application.port.output.ProfissionalPort;
import com.saude.recife.api.application.port.output.UsuarioPort;
import com.saude.recife.api.domain.exception.*;
import com.saude.recife.api.domain.model.Profissional;
import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;

import java.util.List;

public class ProfissionalService
        implements ProfissionalUseCase {

    private final ProfissionalPort profissionalPort;
    private final UsuarioPort usuarioPort;

    public ProfissionalService(
            ProfissionalPort profissionalPort,
            UsuarioPort usuarioPort) {

        this.profissionalPort = profissionalPort;
        this.usuarioPort = usuarioPort;
    }

    @Override
    public Profissional cadastrar(
            String cpf,
            String conselho,
            String registroProfissional,
            String ufRegistro,
            Long idUsuario) {

        if (profissionalPort.existePorCpf(cpf)) {
            throw new CpfJaCadastradoException(cpf);
        }

        if (profissionalPort.existePorUsuario(idUsuario)) {
            throw new UsuarioJaVinculadoException(idUsuario);
        }

        if (profissionalPort.existePorRegistro(
                registroProfissional,
                conselho,
                ufRegistro)) {

            throw new RegistroProfissionalJaCadastradoException(
                    registroProfissional,
                    conselho,
                    ufRegistro
            );
        }

        Usuario usuario = usuarioPort
                .buscarPorId(idUsuario)
                .orElseThrow(
                        () -> new UsuarioNaoEncontradoException(
                                idUsuario
                        )
                );

        if (usuario.getTipoUsuario()
                != TipoUsuario.PROFISSIONAL) {

            throw new UsuarioNaoEhProfissionalException(
                    idUsuario
            );
        }

        Profissional profissional =
                new Profissional(
                        null,
                        cpf,
                        conselho,
                        registroProfissional,
                        ufRegistro,
                        true,
                        idUsuario
                );

        return profissionalPort.salvar(profissional);
    }

    @Override
    public Profissional buscarPorId(Long id) {

        return profissionalPort.buscarPorId(id)
                .orElseThrow(
                        () ->
                                new ProfissionalNaoEncontradoException(
                                        id
                                )
                );
    }

    @Override
    public List<Profissional> listar() {
        return profissionalPort.listar();
    }

    @Override
    public Profissional atualizar(
            Long id,
            String cpf,
            String conselho,
            String registroProfissional,
            String ufRegistro) {

        Profissional existente = buscarPorId(id);

        if (!existente.getCpf().equals(cpf)
                && profissionalPort.existePorCpf(cpf)) {

            throw new CpfJaCadastradoException(cpf);
        }

        boolean registroAlterado =
                !existente.getRegistroProfissional()
                        .equals(registroProfissional)
                        ||
                        !existente.getConselho()
                                .equals(conselho)
                        ||
                        !existente.getUfRegistro()
                                .equals(ufRegistro);

        if (registroAlterado
                && profissionalPort.existePorRegistro(
                registroProfissional,
                conselho,
                ufRegistro)) {

            throw new RegistroProfissionalJaCadastradoException(
                    registroProfissional,
                    conselho,
                    ufRegistro
            );
        }

        Profissional atualizado =
                new Profissional(
                        existente.getId(),
                        cpf,
                        conselho,
                        registroProfissional,
                        ufRegistro,
                        existente.isAtivo(),
                        existente.getIdUsuario()
                );

        return profissionalPort.salvar(atualizado);
    }

    @Override
    public void inativar(Long id) {

        Profissional existente = buscarPorId(id);

        Profissional inativo =
                new Profissional(
                        existente.getId(),
                        existente.getCpf(),
                        existente.getConselho(),
                        existente.getRegistroProfissional(),
                        existente.getUfRegistro(),
                        false,
                        existente.getIdUsuario()
                );

        profissionalPort.salvar(inativo);
    }
}