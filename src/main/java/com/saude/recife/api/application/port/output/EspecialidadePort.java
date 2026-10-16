package com.saude.recife.api.application.port.output;
import com.saude.recife.api.domain.model.Especialidade;
import java.util.List;
import java.util.Optional;

public interface EspecialidadePort {
    Especialidade salvar(Especialidade especialidade);

    Optional<Especialidade> buscarPorId(Long id);

    List<Especialidade> listar();
}
