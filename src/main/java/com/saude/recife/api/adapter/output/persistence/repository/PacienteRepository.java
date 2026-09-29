package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository
        extends JpaRepository<PacienteEntity, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByIdUsuario(Long idUsuario);
}