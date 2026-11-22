package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.PacienteResponse;
import com.saude.recife.api.adapter.input.web.dto.response.PerfilPacienteResponse;
import com.saude.recife.api.domain.model.Paciente;
import com.saude.recife.api.domain.model.Usuario;
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

    public PerfilPacienteResponse paraPerfilResponse(
            Paciente paciente,
            Usuario usuario) {
        return new PerfilPacienteResponse(
                paciente.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                paciente.getCpf(),
                paciente.getDataNascimento(),
                paciente.getSexo()
        );
    }
}