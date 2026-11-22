package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.ClinicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClinicaRepository
        extends JpaRepository<ClinicaEntity, Long> {

    boolean existsByCnpj(String cnpj);

    @Query(value = """
            SELECT EXISTS (
                SELECT 1
                FROM public.clinica_especialidade ce
                WHERE ce.id_clinica = :idClinica
                  AND ce.id_especialidade = :idEspecialidade
            )
            """, nativeQuery = true)
    boolean ofereceEspecialidade(
            @Param("idClinica") Long idClinica,
            @Param("idEspecialidade") Long idEspecialidade
    );
}