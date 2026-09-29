package com.saude.recife.api.application.service;

import com.saude.recife.api.application.port.input.EspecialidadeUseCase;
import com.saude.recife.api.application.port.output.EspecialidadePort;
import com.saude.recife.api.domain.exception.EspecialidadeNaoEncontradaException;
import com.saude.recife.api.domain.model.Especialidade;

import java.util.List;

public class EspecialidadeService implements EspecialidadeUseCase {

    private final EspecialidadePort especialidadePort;

    public EspecialidadeService(EspecialidadePort especialidadePort) {
        this.especialidadePort = especialidadePort;
    }

    @Override
    public Especialidade cadastrar(String nome, String descricao) {
        Especialidade especialidade =
                new Especialidade(null, nome, descricao, true);

        return especialidadePort.salvar(especialidade);
    }

    @Override
    public Especialidade buscarPorId(Long id) {
        return especialidadePort.buscarPorId(id)
                .orElseThrow(() ->
                        new EspecialidadeNaoEncontradaException(id));
    }

    @Override
    public List<Especialidade> listar() {
        return especialidadePort.listar();
    }

    @Override
    public Especialidade atualizar(
            Long id,
            String nome,
            String descricao) {

        Especialidade existente = buscarPorId(id);

        Especialidade atualizada = new Especialidade(
                existente.getId(),
                nome,
                descricao,
                existente.isAtivo()
        );

        return especialidadePort.salvar(atualizada);
    }

    @Override
    public void inativar(Long id) {

        Especialidade existente = buscarPorId(id);

        Especialidade inativa = new Especialidade(
                existente.getId(),
                existente.getNome(),
                existente.getDescricao(),
                false
        );

        especialidadePort.salvar(inativa);
    }
}


