package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.CadastroPacienteUseCase;
import com.saude.recife.api.application.port.input.PacienteUseCase;
import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.domain.model.TipoUsuario;
import com.saude.recife.api.domain.model.Usuario;

import java.time.LocalDate;

public class CadastroPacienteService implements CadastroPacienteUseCase {

    private final UsuarioUseCase usuarioUseCase;
    private final PacienteUseCase pacienteUseCase;

    public CadastroPacienteService(UsuarioUseCase usuarioUseCase, PacienteUseCase pacienteUseCase) {
        this.usuarioUseCase = usuarioUseCase;
        this.pacienteUseCase = pacienteUseCase;
    }

    @Override
    public Usuario cadastrar(String nome, String email, String senha, String telefone,
                             String cpf, LocalDate dataNascimento, String sexo, String observacoes) {
        Usuario usuario = usuarioUseCase.cadastrar(nome, email, senha, telefone, TipoUsuario.PACIENTE);
        pacienteUseCase.cadastrar(cpf, dataNascimento, sexo, observacoes, usuario.getId());
        return usuario;
    }
}
