package com.saude.recife.api.application.port.input;

import com.saude.recife.api.domain.model.Clinica;

import java.util.List;

public interface ClinicaUseCase {

    Clinica cadastrar(
            String nome,
            String cnpj,
            String telefone,
            String email,
            String logradouro,
            String numero,
            String bairro,
            String cidade,
            String uf
    );

    Clinica buscarPorId(Long id);

    List<Clinica> listar();

    Clinica atualizar(
            Long id,
            String nome,
            String cnpj,
            String telefone,
            String email,
            String logradouro,
            String numero,
            String bairro,
            String cidade,
            String uf
    );

    void inativar(Long id);
}