package com.saude.recife.api.adapter.output.persistence.mapper;

import com.saude.recife.api.adapter.output.persistence.entity.PacienteEntity;
import com.saude.recife.api.domain.model.Paciente;
import org.springframework.stereotype.Component;

@Component
public class PacientePersistenceMapper {

    public PacienteEntity paraEntity(Paciente paciente) {

        return new PacienteEntity(
                paciente.getId(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getSexo(),
                paciente.getObservacoes(),
                paciente.getIdUsuario()
        );
    }

    public Paciente paraDomain(PacienteEntity entity) {

        return new Paciente(
                entity.getId(),
                entity.getCpf(),
                entity.getDataNascimento(),
                entity.getSexo(),
                entity.getObservacoes(),
                entity.getIdUsuario()
        );
    }
}