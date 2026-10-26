package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.AgendamentoEntity;
import com.saude.recife.api.domain.model.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository
        extends JpaRepository<AgendamentoEntity, Long> {

    Optional<AgendamentoEntity>
    findByIdAndIdPaciente(
            Long id,
            Long idPaciente
    );

    List<AgendamentoEntity>
    findByIdPacienteOrderByInicioDesc(
            Long idPaciente
    );

    @Query("""
        SELECT COUNT(a)
        FROM AgendamentoEntity a
        WHERE a.idProfissional = :idProfissional
          AND a.status = :status
          AND a.inicio < :fim
          AND a.fim > :inicio
    """)
    long contarConflitosProfissional(

            @Param("idProfissional")
            Long idProfissional,

            @Param("inicio")
            OffsetDateTime inicio,

            @Param("fim")
            OffsetDateTime fim,

            @Param("status")
            StatusAgendamento status
    );
}