package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.Profissional;

import java.util.List;
import java.util.Optional;

public interface ProfissionalPort {

    Profissional salvar(Profissional profissional);

    Optional<Profissional> buscarPorId(Long id);

    Optional<Profissional> buscarPorUsuario(Long idUsuario);

    List<Profissional> listar();

    boolean existePorCpf(String cpf);

    boolean existePorUsuario(Long idUsuario);

    boolean existePorRegistro(
            String registroProfissional,
            String conselho,
            String ufRegistro
    );

    boolean estaVinculadoAClinica(Long idProfissional, Long idClinica);

    boolean possuiEspecialidade(Long idProfissional, Long idEspecialidade);
}