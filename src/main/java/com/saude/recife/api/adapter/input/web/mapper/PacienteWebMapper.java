package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.PacienteResponse;
import com.saude.recife.api.domain.model.Paciente;
import org.springframework.stereotype.Component;

@Component
public class PacienteWebMapper {

    public PacienteResponse paraResponse(Paciente paciente) {

        return new PacienteResponse(
                paciente.getId(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getSexo(),
                paciente.getObservacoes(),
                paciente.getIdUsuario()
        );
    }
}