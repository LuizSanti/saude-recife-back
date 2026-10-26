package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.CadastroProfissionalUseCase;
import com.saude.recife.api.application.port.input.ProfissionalUseCase;
import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.domain.model.Profissional;
import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;

public class CadastroProfissionalService implements CadastroProfissionalUseCase {

    private final UsuarioUseCase usuarioUseCase;
    private final ProfissionalUseCase profissionalUseCase;

    public CadastroProfissionalService(UsuarioUseCase usuarioUseCase,
                                       ProfissionalUseCase profissionalUseCase) {
        this.usuarioUseCase = usuarioUseCase;
        this.profissionalUseCase = profissionalUseCase;
    }

    @Override
    public Profissional cadastrar(String nome, String email, String senha, String telefone,
                                  String cpf, String conselho, String registroProfissional,
                                  String ufRegistro) {
        Usuario usuario = usuarioUseCase.cadastrar(nome, email, senha, telefone,
                TipoUsuario.PROFISSIONAL);
        return profissionalUseCase.cadastrar(cpf, conselho, registroProfissional,
                ufRegistro, usuario.getId());
    }
}
