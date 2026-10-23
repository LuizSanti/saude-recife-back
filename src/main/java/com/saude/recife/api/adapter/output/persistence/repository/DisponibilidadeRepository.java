package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.DisponibilidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

    public interface DisponibilidadeRepository
            extends JpaRepository<DisponibilidadeEntity, Long> {

        @Query("""
            SELECT d
            FROM DisponibilidadeEntity d
            WHERE d.data = :data
              AND d.ativo = true
              AND (:clinicaId IS NULL
                   OR d.idClinica = :clinicaId)
              AND (:especialidadeId IS NULL
                   OR d.idEspecialidade = :especialidadeId)
              AND (:profissionalId IS NULL
                   OR d.idProfissional = :profissionalId)
            ORDER BY d.horaInicio
            """)
        List<DisponibilidadeEntity> consultar(
                @Param("data") LocalDate data,
                @Param("clinicaId") Long clinicaId,
                @Param("especialidadeId") Long especialidadeId,
                @Param("profissionalId") Long profissionalId
        );

        @Query("""
            SELECT COUNT(d)
            FROM DisponibilidadeEntity d
            WHERE d.idProfissional = :profissionalId
              AND d.idClinica = :clinicaId
              AND d.data = :data
              AND d.ativo = true
              AND d.horaInicio < :horaFim
              AND d.horaFim > :horaInicio
            """)
        long contarSobreposicoes(
                @Param("profissionalId") Long profissionalId,
                @Param("clinicaId") Long clinicaId,
                @Param("data") LocalDate data,
                @Param("horaInicio") LocalTime horaInicio,
                @Param("horaFim") LocalTime horaFim
        );
    }

