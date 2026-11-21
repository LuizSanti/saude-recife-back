package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.AgendamentoExameEntity;
import com.saude.recife.api.domain.model.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

public interface AgendamentoExameRepository
        extends JpaRepository<AgendamentoExameEntity, Long> {

    @Query("""
            SELECT COUNT(a)
            FROM AgendamentoExameEntity a
            WHERE a.exameId = :exameId
              AND a.clinicaId = :clinicaId
              AND a.status = :status
              AND a.inicio < :fim
              AND a.fim > :inicio
            """)
    long contarConflitos(
            @Param("exameId") Long exameId,
            @Param("clinicaId") Long clinicaId,
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("status") StatusAgendamento status
    );
}
