package com.saude.recife.api.adapter.out.persistence.adapter;

import com.saude.recife.api.adapter.out.persistence.entity.UsuarioEntity;
import com.saude.recife.api.adapter.out.persistence.mapper.UsuarioPersistenceMapper;
import com.saude.recife.api.adapter.out.persistence.repository.UsuarioRepository;
import com.saude.recife.api.domain.model.Usuario;
import com.saude.recife.api.domain.port.out.UsuarioPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioPersistenceAdapter implements UsuarioPort {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioPersistenceMapper mapper;

    public UsuarioPersistenceAdapter(UsuarioRepository usuarioRepository,
                                     UsuarioPersistenceMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = mapper.paraEntity(usuario);
        UsuarioEntity salvo = usuarioRepository.save(entity);
        return mapper.paraDomain(salvo);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(mapper::paraDomain);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(mapper::paraDomain);
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
