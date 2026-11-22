package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.ProfissionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfissionalRepository
        extends JpaRepository<ProfissionalEntity, Long> {

    Optional<ProfissionalEntity> findByIdUsuario(Long idUsuario);

    boolean existsByCpf(String cpf);

    boolean existsByIdUsuario(Long idUsuario);

    boolean existsByRegistroProfissionalAndConselhoAndUfRegistro(
            String registroProfissional,
            String conselho,
            String ufRegistro
    );

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM public.profissional_clinica pc
                WHERE pc.id_profissional = :idProfissional
                  AND pc.id_clinica = :idClinica
            )
            """, nativeQuery = true)
    boolean estaVinculadoAClinica(
            @Param("idProfissional") Long idProfissional,
            @Param("idClinica") Long idClinica
    );

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM public.profissional_especialidade pe
                WHERE pe.id_profissional = :idProfissional
                  AND pe.id_especialidade = :idEspecialidade
            )
            """, nativeQuery = true)
    boolean possuiEspecialidade(
            @Param("idProfissional") Long idProfissional,
            @Param("idEspecialidade") Long idEspecialidade
    );
}