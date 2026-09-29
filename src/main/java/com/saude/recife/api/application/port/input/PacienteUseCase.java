package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.Paciente;

import java.time.LocalDate;
import java.util.List;

public interface PacienteUseCase {

    Paciente cadastrar(
            String cpf,
            LocalDate dataNascimento,
            String sexo,
            String observacoes,
            Long idUsuario
    );

    Paciente buscarPorId(Long id);

    List<Paciente> listar();

    Paciente atualizar(
            Long id,
            String cpf,
            LocalDate dataNascimento,
            String sexo,
            String observacoes
    );
}