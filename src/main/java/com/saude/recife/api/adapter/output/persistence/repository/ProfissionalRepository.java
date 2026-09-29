package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository
        extends JpaRepository<ProfissionalEntity, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByIdUsuario(Long idUsuario);

    boolean existsByRegistroProfissionalAndConselhoAndUfRegistro(
            String registroProfissional,
            String conselho,
            String ufRegistro
    );
}