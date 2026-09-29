package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.Profissional;

import java.util.List;

public interface ProfissionalUseCase {

    Profissional cadastrar(
            String cpf,
            String conselho,
            String registroProfissional,
            String ufRegistro,
            Long idUsuario
    );

    Profissional buscarPorId(Long id);

    List<Profissional> listar();

    Profissional atualizar(
            Long id,
            String cpf,
            String conselho,
            String registroProfissional,
            String ufRegistro
    );

    void inativar(Long id);
}