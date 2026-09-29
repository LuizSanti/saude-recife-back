package com.saude.recife.api.application.port.input;
import com.saude.recife.api.domain.model.Especialidade;
import java.util.List;

public interface EspecialidadeUseCase {
    Especialidade cadastrar(String nome, String descricao);

    Especialidade buscarPorId(Long id);

    List<Especialidade> listar();

    Especialidade atualizar(Long id, String nome, String descricao);

    void inativar(Long id);
}
