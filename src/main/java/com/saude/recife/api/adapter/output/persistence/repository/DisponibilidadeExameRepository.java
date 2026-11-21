package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.DisponibilidadeExameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DisponibilidadeExameRepository
        extends JpaRepository<DisponibilidadeExameEntity, Long> {

    @Query("""
            SELECT d
            FROM DisponibilidadeExameEntity d
            WHERE d.data = :data
              AND d.ativo = true
              AND (:clinicaId IS NULL OR d.clinicaId = :clinicaId)
              AND (:exameId IS NULL OR d.exameId = :exameId)
              AND EXISTS (
                  SELECT e.id
                  FROM ExameEntity e
                  WHERE e.id = d.exameId
                    AND e.ativo = true
              )
            ORDER BY d.horaInicio
            """)
    List<DisponibilidadeExameEntity> consultar(
            @Param("data") LocalDate data,
            @Param("clinicaId") Long clinicaId,
            @Param("exameId") Long exameId
    );
}
