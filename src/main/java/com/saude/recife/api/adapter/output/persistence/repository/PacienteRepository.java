package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository
        extends JpaRepository<PacienteEntity, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByIdUsuario(Long idUsuario);

    Optional<PacienteEntity> findByIdUsuario(Long idUsuario);
}