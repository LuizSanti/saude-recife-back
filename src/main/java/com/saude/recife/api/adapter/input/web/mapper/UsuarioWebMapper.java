package com.saude.recife.api.adapter.input.web.mapper;

import com.saude.recife.api.adapter.input.web.dto.response.UsuarioResponse;
import com.saude.recife.api.domain.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioWebMapper {

    public UsuarioResponse paraResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getTipoUsuario(),
                usuario.isAtivo(),
                usuario.getCriadoEm()
        );
    }

    public List<UsuarioResponse> paraListaResponse(List<Usuario> usuarios) {
        return usuarios.stream().map(this::paraResponse).toList();
    }
}
