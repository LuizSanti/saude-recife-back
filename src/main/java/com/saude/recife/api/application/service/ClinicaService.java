package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.ClinicaUseCase;
import com.saude.recife.api.application.port.output.ClinicaPort;
import com.saude.recife.api.domain.exception.ClinicaNaoEncontradaException;
import com.saude.recife.api.domain.exception.CnpjJaCadastradoException;
import com.saude.recife.api.domain.model.Clinica;

import java.util.List;

public class ClinicaService implements ClinicaUseCase {

    private final ClinicaPort clinicaPort;

    public ClinicaService(ClinicaPort clinicaPort) {
        this.clinicaPort = clinicaPort;
    }

    @Override
    public Clinica cadastrar(
            String nome,
            String cnpj,
            String telefone,
            String email,
            String logradouro,
            String numero,
            String bairro,
            String cidade,
            String uf) {

        if (clinicaPort.existePorCnpj(cnpj)) {
            throw new CnpjJaCadastradoException(cnpj);
        }

        Clinica clinica = new Clinica(
                null,
                nome,
                cnpj,
                telefone,
                email,
                logradouro,
                numero,
                bairro,
                cidade,
                uf,
                true
        );

        return clinicaPort.salvar(clinica);
    }

    @Override
    public Clinica buscarPorId(Long id) {

        return clinicaPort.buscarPorId(id)
                .orElseThrow(() ->
                        new ClinicaNaoEncontradaException(id));
    }

    @Override
    public List<Clinica> listar() {
        return clinicaPort.listar();
    }

    @Override
    public Clinica atualizar(
            Long id,
            String nome,
            String cnpj,
            String telefone,
            String email,
            String logradouro,
            String numero,
            String bairro,
            String cidade,
            String uf) {

        Clinica existente = buscarPorId(id);

        if (!existente.getCnpj().equals(cnpj)
                && clinicaPort.existePorCnpj(cnpj)) {

            throw new CnpjJaCadastradoException(cnpj);
        }

        Clinica atualizada = new Clinica(
                existente.getId(),
                nome,
                cnpj,
                telefone,
                email,
                logradouro,
                numero,
                bairro,
                cidade,
                uf,
                existente.isAtivo()
        );

        return clinicaPort.salvar(atualizada);
    }

    @Override
    public void inativar(Long id) {

        Clinica existente = buscarPorId(id);

        Clinica inativa = new Clinica(
                existente.getId(),
                existente.getNome(),
                existente.getCnpj(),
                existente.getTelefone(),
                existente.getEmail(),
                existente.getLogradouro(),
                existente.getNumero(),
                existente.getBairro(),
                existente.getCidade(),
                existente.getUf(),
                false
        );

        clinicaPort.salvar(inativa);
    }
}