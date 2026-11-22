package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.AgendamentoEntity;
import com.saude.recife.api.adapter.output.persistence.projection.AgendamentoPainelProjection;
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

    Optional<AgendamentoEntity> findByIdAndIdProfissional(
            Long id,
            Long idProfissional
    );

    List<AgendamentoEntity> findByIdProfissionalOrderByInicioAsc(
            Long idProfissional
    );

    @Query("""
        SELECT new com.saude.recife.api.adapter.output.persistence.projection.AgendamentoPainelProjection(
            a.id,
            usuarioPaciente.nome,
            usuarioProfissional.nome,
            clinica.nome,
            especialidade.nome,
            a.inicio,
            a.fim,
            a.status
        )
        FROM AgendamentoEntity a,
             PacienteEntity paciente,
             UsuarioEntity usuarioPaciente,
             ProfissionalEntity profissional,
             UsuarioEntity usuarioProfissional,
             ClinicaEntity clinica,
             EspecialidadeEntity especialidade
        WHERE paciente.id = a.idPaciente
          AND usuarioPaciente.id = paciente.idUsuario
          AND profissional.id = a.idProfissional
          AND usuarioProfissional.id = profissional.idUsuario
          AND clinica.id = a.idClinica
          AND especialidade.id = a.idEspecialidade
        ORDER BY a.inicio DESC
        """)
    List<AgendamentoPainelProjection> listarPainel();

    @Query("""
        SELECT new com.saude.recife.api.adapter.output.persistence.projection.AgendamentoPainelProjection(
            a.id,
            usuarioPaciente.nome,
            usuarioProfissional.nome,
            clinica.nome,
            especialidade.nome,
            a.inicio,
            a.fim,
            a.status
        )
        FROM AgendamentoEntity a,
             PacienteEntity paciente,
             UsuarioEntity usuarioPaciente,
             ProfissionalEntity profissional,
             UsuarioEntity usuarioProfissional,
             ClinicaEntity clinica,
             EspecialidadeEntity especialidade
        WHERE paciente.id = a.idPaciente
          AND usuarioPaciente.id = paciente.idUsuario
          AND profissional.id = a.idProfissional
          AND usuarioProfissional.id = profissional.idUsuario
          AND clinica.id = a.idClinica
          AND especialidade.id = a.idEspecialidade
          AND a.idProfissional = :idProfissional
        ORDER BY a.inicio DESC
        """)
    List<AgendamentoPainelProjection> listarPainelPorProfissional(
            @Param("idProfissional") Long idProfissional
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