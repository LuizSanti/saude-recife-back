package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.DisponibilidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

    public interface DisponibilidadeRepository
            extends JpaRepository<DisponibilidadeEntity, Long> {

        List<DisponibilidadeEntity> findByIdProfissionalOrderByDataAscHoraInicioAsc(
                Long idProfissional
        );

        Optional<DisponibilidadeEntity> findByIdAndIdProfissional(
                Long id,
                Long idProfissional
        );

        @Query(value = """
                SELECT d.*
                FROM public.disponibilidade d
                JOIN public.profissional p
                  ON p.id_profissional = d.id_profissional
                 AND p.ativo = TRUE
                JOIN public.clinica c
                  ON c.id_clinica = d.id_clinica
                 AND c.ativo = TRUE
                JOIN public.especialidade e
                  ON e.id_especialidade = d.id_especialidade
                 AND e.ativo = TRUE
                WHERE d.data = :data
                  AND d.ativo = TRUE
                  AND (:clinicaId IS NULL OR d.id_clinica = :clinicaId)
                  AND (:especialidadeId IS NULL OR d.id_especialidade = :especialidadeId)
                  AND (:profissionalId IS NULL OR d.id_profissional = :profissionalId)
                  AND EXISTS (
                      SELECT 1 FROM public.profissional_clinica pc
                      WHERE pc.id_profissional = d.id_profissional
                        AND pc.id_clinica = d.id_clinica
                  )
                  AND EXISTS (
                      SELECT 1 FROM public.profissional_especialidade pe
                      WHERE pe.id_profissional = d.id_profissional
                        AND pe.id_especialidade = d.id_especialidade
                  )
                  AND EXISTS (
                      SELECT 1 FROM public.clinica_especialidade ce
                      WHERE ce.id_clinica = d.id_clinica
                        AND ce.id_especialidade = d.id_especialidade
                  )
                ORDER BY d.hora_inicio
                """, nativeQuery = true)
        List<DisponibilidadeEntity> consultar(
                @Param("data") LocalDate data,
                @Param("clinicaId") Long clinicaId,
                @Param("especialidadeId") Long especialidadeId,
                @Param("profissionalId") Long profissionalId
        );

        @Query(value = """
                SELECT EXISTS (
                    SELECT 1
                    FROM public.disponibilidade d
                    JOIN public.profissional p
                      ON p.id_profissional = d.id_profissional
                     AND p.ativo = TRUE
                    JOIN public.clinica c
                      ON c.id_clinica = d.id_clinica
                     AND c.ativo = TRUE
                    JOIN public.especialidade e
                      ON e.id_especialidade = d.id_especialidade
                     AND e.ativo = TRUE
                    WHERE d.id_disponibilidade = :idDisponibilidade
                      AND d.ativo = TRUE
                      AND EXISTS (
                          SELECT 1 FROM public.profissional_clinica pc
                          WHERE pc.id_profissional = d.id_profissional
                            AND pc.id_clinica = d.id_clinica
                      )
                      AND EXISTS (
                          SELECT 1 FROM public.profissional_especialidade pe
                          WHERE pe.id_profissional = d.id_profissional
                            AND pe.id_especialidade = d.id_especialidade
                      )
                      AND EXISTS (
                          SELECT 1 FROM public.clinica_especialidade ce
                          WHERE ce.id_clinica = d.id_clinica
                            AND ce.id_especialidade = d.id_especialidade
                      )
                )
                """, nativeQuery = true)
        boolean possuiAssociacoesValidas(
                @Param("idDisponibilidade") Long idDisponibilidade
        );

        @Query("""
            SELECT COUNT(d)
            FROM DisponibilidadeEntity d
            WHERE d.idProfissional = :profissionalId
              AND d.data = :data
              AND d.ativo = true
              AND d.horaInicio < :horaFim
              AND d.horaFim > :horaInicio
            """)
        long contarSobreposicoes(
                @Param("profissionalId") Long profissionalId,
                @Param("data") LocalDate data,
                @Param("horaInicio") LocalTime horaInicio,
                @Param("horaFim") LocalTime horaFim
        );
    }

