package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.Clinica;

import java.util.List;
import java.util.Optional;

public interface ClinicaPort {

    Clinica salvar(Clinica clinica);

    Optional<Clinica> buscarPorId(Long id);

    List<Clinica> listar();

    boolean existePorCnpj(String cnpj);
}