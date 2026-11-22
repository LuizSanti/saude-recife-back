package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.ProfissionalResponse;
import com.saude.recife.api.adapter.input.web.dto.response.ProfissionalPerfilResponse;
import com.saude.recife.api.application.port.input.UsuarioUseCase;
import com.saude.recife.api.domain.model.Profissional;
import com.saude.recife.api.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ProfissionalWebMapper {
    private final UsuarioUseCase usuarioUseCase;

    public ProfissionalWebMapper(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    public ProfissionalResponse paraResponse(
            Profissional profissional) {

        Usuario usuario = usuarioUseCase.buscarPorId(profissional.getIdUsuario());
        return new ProfissionalResponse(
                profissional.getId(),
                usuario.getNome(),
                profissional.getCpf(),
                profissional.getConselho(),
                profissional.getRegistroProfissional(),
                profissional.getUfRegistro(),
                profissional.isAtivo(),
                profissional.getIdUsuario()
        );
    }

    public ProfissionalPerfilResponse paraPerfilResponse(
            Profissional profissional) {

        Usuario usuario = usuarioUseCase.buscarPorId(profissional.getIdUsuario());
        return new ProfissionalPerfilResponse(
                profissional.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                profissional.getCpf(),
                profissional.getConselho(),
                profissional.getRegistroProfissional(),
                profissional.getUfRegistro(),
                profissional.isAtivo()
        );
    }
}