package com.saude.recife.api.adapter.output.persistence.mapper;

import com.saude.recife.api.adapter.output.persistence.entity.UsuarioEntity;
import com.saude.recife.api.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioPersistenceMapper {

    public UsuarioEntity paraEntity(Usuario usuario) {
        return new UsuarioEntity(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenhaHash(),
                usuario.getTelefone(),
                usuario.getTipoUsuario(),
                usuario.isAtivo(),
                usuario.getCriadoEm()
        );
    }

    public Usuario paraDomain(UsuarioEntity entity) {
        return new Usuario(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getSenhaHash(),
                entity.getTelefone(),
                entity.getTipoUsuario(),
                entity.isAtivo(),
                entity.getCriadoEm()
        );
    }
}
