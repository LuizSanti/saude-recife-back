package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.Profissional;

import java.util.List;
import java.util.Optional;

public interface ProfissionalPort {

    Profissional salvar(Profissional profissional);

    Optional<Profissional> buscarPorId(Long id);

    List<Profissional> listar();

    boolean existePorCpf(String cpf);

    boolean existePorUsuario(Long idUsuario);

    boolean existePorRegistro(
            String registroProfissional,
            String conselho,
            String ufRegistro
    );
}