package com.saude.recife.api.adapter.output.persistence.adapter;

import com.saude.recife.api.adapter.output.persistence.entity.UsuarioEntity;
import com.saude.recife.api.adapter.output.persistence.mapper.UsuarioPersistenceMapper;
import com.saude.recife.api.adapter.output.persistence.repository.UsuarioRepository;
import com.saude.recife.api.domain.model.Usuario;
import com.saude.recife.api.application.port.output.UsuarioPort;
import org.springframework.stereotype.Component;

import java.util.List;
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

    @Override
    public Usuario atualizar(Usuario usuario) {
        return salvar(usuario);
    }

    @Override
    public void desativar(Long id) {
        buscarPorId(id).ifPresent(usuario -> {
            usuario.desativar();
            salvar(usuario);
        });
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(mapper::paraDomain)
                .toList();
    }
}
