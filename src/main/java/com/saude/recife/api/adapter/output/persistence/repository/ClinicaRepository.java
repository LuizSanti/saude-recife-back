package com.saude.recife.api.adapter.output.persistence.repository;

import com.saude.recife.api.adapter.output.persistence.entity.ClinicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicaRepository
        extends JpaRepository<ClinicaEntity, Long> {

    boolean existsByCnpj(String cnpj);
}