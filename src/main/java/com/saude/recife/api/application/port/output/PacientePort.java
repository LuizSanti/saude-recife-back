package com.saude.recife.api.application.port.output;

import com.saude.recife.api.domain.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacientePort {

    Paciente salvar(Paciente paciente);

    Optional<Paciente> buscarPorId(Long id);

    List<Paciente> listar();

    boolean existePorCpf(String cpf);

    boolean existePorUsuario(Long idUsuario);
}